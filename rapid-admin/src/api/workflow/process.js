
import request from '@/utils/request'

export function getAll() {
  return request({
    url: 'api/processes',
    method: 'get'
  })
}

// export function get(id) {
//   return request({
//     url: 'api/articles/' + id,
//     method: 'get'
//   })
// }

// export function create(data) {
//   return request({
//     url: 'api/articles',
//     method: 'post',
//     data
//   })
// }

// export function update(data) {
//   return request({
//     url: 'api/articles',
//     method: 'put',
//     data
//   })
// }

// export function del(ids) {
//   return request({
//     url: 'api/articles',
//     method: 'delete',
//     data: ids
//   })
// }

export default { getAll }
