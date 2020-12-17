
import request from '@/utils/request'

export function getAll() {
  return request({
    url: 'api/processes',
    method: 'get'
  })
}

export function start(id) {
  return request({
    url: 'api/processes/start/' + id,
    method: 'post'
  })
}

export function del(id) {
  return request({
    url: 'api/processes/delete/' + id,
    method: 'delete',
    data: id
  })
}

export function currentList() {
  return request({
    url: 'api/processes/currentList',
    method: 'get'
  })
}

export function historyList() {
  return request({
    url: 'api/processes/historyList',
    method: 'get'
  })
}

export function detail(id) {
  return request({
    url: 'api/processes/detail/' + id,
    method: 'get'
  })
}

export default { getAll, start, del, currentList, historyList, detail }
