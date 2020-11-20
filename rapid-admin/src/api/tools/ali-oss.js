import request from '@/utils/request'

export function getFolders(params) {
  return request({
    url: 'api/ali-oss',
    method: 'get',
    params
  })
}

export function queryConfig() {
  return request({
    url: 'api/ali-oss/config',
    method: 'get'
  })
}

export function updateConfig(data) {
  return request({
    url: 'api/ali-oss/config',
    data,
    method: 'put'
  })
}

export default { getFolders, queryConfig, updateConfig }
