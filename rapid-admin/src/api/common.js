import request from '@/utils/request'

// 通用-查询
export function query(url, params) {
  return request({
    url: url,
    params: params,
    method: 'get'
  })
}

// 通用-导出
export function download(url, params) {
  return request({
    url: url,
    params: params,
    method: 'get',
    responseType: 'blob'
  })
}
