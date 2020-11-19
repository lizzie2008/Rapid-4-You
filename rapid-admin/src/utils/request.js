import axios from 'axios'
import qs from 'qs'
import { Message, Notification } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import Cookies from 'js-cookie'

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
    if (Object.prototype.hasOwnProperty.call(res, 'code')) {
      if (res.code !== 0) {
        Message({
          message: res.msg || 'Error',
          type: 'error',
          duration: 5 * 1000
        })
        return Promise.reject(new Error(res.msg || 'Error'))
      } else {
        return Promise.resolve(res.data)
      }
    } else {
      return Promise.resolve(res)
    }
  },
  error => {
    console.log('err' + error) // for debug
    const res = error.response
    if (!res) Notification.error('无法从后台服务器返回数据，请联系管理员！')
    if (res.status === 401 || res.status === 403) {
      // 重登录
      store.dispatch('user/logout').then(() => {
        // 用户登录界面提示
        Cookies.set('SessionExpired', true)
        location.reload()
      })
    } else if (res.status === 404) {
      Message({
        message: '后台服务器不存在此接口，请联系管理员！',
        type: 'error',
        duration: 5 * 1000
      })
    } else {
      Message({
        message: res.data.message || '未知错误，请联系管理员！',
        type: 'error',
        duration: 5 * 1000
      })
    }
    return Promise.reject(error)
  }
)

export default service
