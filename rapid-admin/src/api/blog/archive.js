
import request from '@/utils/request'

export function getAll(params) {
  return request({
    url: 'api/archives',
    method: 'get',
    params
  })
}

export default { getAll }
