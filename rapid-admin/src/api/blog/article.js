
import request from '@/utils/request'

export function getAll(params) {
  return request({
    url: 'api/articles',
    method: 'get',
    params
  })
}

export function get(id) {
  return request({
    url: 'api/articles/' + id,
    method: 'get'
  })
}

export function update(data) {
  return request({
    url: 'api/articles',
    method: 'put',
    data
  })
}

export default { getAll, get, update }
