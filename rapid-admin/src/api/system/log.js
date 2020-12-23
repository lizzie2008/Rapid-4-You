import request from '@/utils/request'

export function currentUser(params) {
  return request({
    url: 'api/log/currentUser',
    method: 'get',
    params: params
  })
}

export default { currentUser }

