
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

export function create(data) {
  return request({
    url: 'api/articles',
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: 'api/articles',
    method: 'put',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/articles',
    method: 'delete',
    data: ids
  })
}

export default { getAll, get, create, update, del }
