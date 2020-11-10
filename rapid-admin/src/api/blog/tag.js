
import request from '@/utils/request'

export function getTags(params) {
  return request({
    url: 'api/tag',
    method: 'get',
    params
  })
}

export default { getTags }
