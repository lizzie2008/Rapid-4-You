import { login, getInfo, logout } from '../../api/login'
import { getToken, setToken, removeToken } from '../../utils/auth'

const user = {
  state: {
    token: getToken(),
    user: {},
    roles: [],
    loadMenus: false
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_USER: (state, user) => {
      state.user = user
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_LOAD_MENUS: (state, loadMenus) => {
      state.loadMenus = loadMenus
    }
  },

  actions: {
    // user login
    Login({ commit }, { token, user }) {
      // save user token
      commit('SET_TOKEN', token)
      
      /* save user roles, if no roles, give a default role
        */
      user.roles.length === 0 ?
        commit('SET_ROLES', ['ROLE_SYSTEM_DEFAULT']) : commit('SET_ROLES', user.roles)
      // save user info
      commit('SET_USER', user.user)
      // for first load menu, see src/permission.js
      commit('SET_LOAD_MENUS', true)
    },

    // 获取用户信息
    GetInfo({ commit }) {
      return new Promise((resolve, reject) => {
        getInfo().then(res => {
          setUserInfo(res, commit)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 登出
    LogOut({ commit }) {
      return new Promise((resolve, reject) => {
        logout().then(res => {
          logOut(commit)
          resolve(res)
        }).catch(error => {
          logOut(commit)
          reject(error)
        })
      })
    },

    updateLoadMenus({ commit }) {
      return new Promise(() => {
        commit('SET_LOAD_MENUS', false)
      })
    }
  }
}

export const logOut = (commit) => {
  commit('SET_TOKEN', '')
  commit('SET_ROLES', [])
  removeToken()
}

export const setUserInfo = (res, commit) => {
  // 如果没有任何权限，则赋予一个默认的权限，避免请求死循环
  if (res.roles.length === 0) {
    commit('SET_ROLES', ['ROLE_SYSTEM_DEFAULT'])
  } else {
    commit('SET_ROLES', res.roles)
  }
  commit('SET_USER', res.user)
}

export default user
