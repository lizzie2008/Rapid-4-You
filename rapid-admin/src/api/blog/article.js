
import request from '@/utils/request'

export function getArticles(params) {
  return request({
    url: 'api/articles',
    method: 'get',
    params
  })
}

export default { getArticles }
