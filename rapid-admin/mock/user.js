
const tokens = {
  admin: {
    token: 'admin-token'
  },
  editor: {
    token: 'editor-token'
  }
}

const users = {
  'admin-token': {
    roles: ['admin'],
    introduction: 'I am a super administrator',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Super Admin'
  },
  'editor-token': {
    roles: ['editor'],
    introduction: 'I am an editor',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Normal Editor'
  }
}

module.exports = [
  // user login
  {
    url: '/vue-element-admin/user/login',
    type: 'post',
    response: config => {
      const { username } = config.body
      const token = tokens[username]

      // mock error
      if (!token) {
        return {
          code: 60204,
          message: 'Account and password are incorrect.'
        }
      }

      return {
        code: 20000,
        data: token
      }
    }
  },

  // user loginCode
  {
    url: '/vue-element-admin/user/loginCode',
    type: 'get',
    response: config => {
      return {
        code: 20000,
        data: {
          uuid: "code-key-4d76e3789a1045c4867efe4ae803c534",
          img: "data:image/png;base64,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"
        }
      }
    }
  },

  // get user info
  {
    url: '/vue-element-admin/user/info\.*',
    type: 'get',
    response: config => {
      
      const { token } = config.query
      const info = users[token]

      // mock error
      if (!info) {
        return {
          code: 50008,
          message: 'Login failed, unable to get user details.'
        }
      }

      return {
        code: 20000,
        data: info
      }
    }
  },

  // user logout
  {
    url: '/vue-element-admin/user/logout',
    type: 'post',
    response: _ => {
      return {
        code: 20000,
        data: 'success'
      }
    }
  }
]
