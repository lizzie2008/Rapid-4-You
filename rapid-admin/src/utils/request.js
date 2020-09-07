import axios from 'axios'
import qs from 'qs'
import { Message, Notification } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import router from '@/router'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  // timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['Authorization'] = getToken()
    }

    // 如果是get请求的数组参数，序列化转换：p=[a,b,c] => p=a,b,c
    if (config.method === 'get') {
      config.paramsSerializer = function(params) {
        return qs.stringify(params, { indices: false })
      }
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data
    if (res.code && res.code !== 0 && res.data) {
      Message({
        message: res.msg || 'Error',
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(new Error(res.msg || 'Error'))
    } else {
      return Promise.resolve(res.data)
    }
  },
  error => {
    console.log('err' + error) // for debug
    const res = error.response
    if (!res) Notification.error('无法从后台服务器返回数据')
    if (res.status === 401 || res.status === 403) {
      Notification.warning('当前登录信息已失效，请重新登录')
      // 重登录
      store.dispatch('user/resetToken').then(() => {
        // location.reload()
        router.push('login')
      })
    } else {
      Message({
        message: res.data.message || '未知错误',
        type: 'error',
        duration: 5 * 1000
      })
    }
    return Promise.reject(error)
  }
)

export default service