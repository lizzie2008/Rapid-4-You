import request from '@/utils/request'

export function getFolders(params) {
  return request({
    url: 'api/ali-oss',
    method: 'get',
    params
  })
}
