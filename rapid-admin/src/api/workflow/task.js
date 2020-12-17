
import request from '@/utils/request'

export function getAll() {
  return request({
    url: 'api/tasks',
    method: 'get'
  })
}

export function getFormProperties(id) {
  return request({
    url: 'api/tasks/getFormProperties/' + id,
    method: 'get'
  })
}

export function complete(id, data) {
  return request({
    url: 'api/tasks/complete/' + id,
    method: 'post',
    data
  })
}

export default { getAll, getFormProperties, complete }
