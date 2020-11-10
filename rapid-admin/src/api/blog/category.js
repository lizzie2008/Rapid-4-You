
import request from '@/utils/request'

export function getCategorys(params) {
  return request({
    url: 'api/category',
    method: 'get',
    params
  })
}

export default { getCategorys }
