import Cookies from 'js-cookie'
import Config from '../settings'

const TokenKey = Config.TokenKey

// 获取当前Token
export function getToken() {
  return Cookies.get(TokenKey)
}
// 设置当前Token
export function setToken(token, rememberMe) {
  // 如果记住我，保存Token一段时间，否则登出的时候删除
  if (rememberMe) {
    return Cookies.set(TokenKey, token, { expires: Config.tokenCookieExpires })
  } else return Cookies.set(TokenKey, token)
}
// 删除当前Token
export function removeToken() {
  return Cookies.remove(TokenKey)
}
