import { isAdmin, logout_ } from '@/api/requests/rq-manage.js'

const state = {
  account: localStorage.username,
  token: localStorage.token,
  failure_time: localStorage.failure_time,
  permissions: []
}

const getters = {
  isLogin: state => {
    let retn = false
    if (state.token != null) {
      let unix = Date.parse(new Date())
      if (unix < state.failure_time * 1000) {
        retn = true
      }
    }
    return retn
  }
}

const actions = {
  login ({ commit }, data) {
    return new Promise(resolve => {

      commit('setUserData', {})
      resolve({
        isSuccess: true,
        msg: 'aaa'
      })
    })
  },
  logout ({ commit }) {
    logout_()
    window.location.href = '/home'

  },
  // 获取我的权限
  getPermissions ({ state, commit }) {
    return new Promise(resolve => {
      isAdmin().then(result => {
        let per = [
          'permission.browse',
          'permission.create',
          'permission.edit',
          'permission.remove'
        ]
        if (result) {
          per.push('admin')
          localStorage.setItem('isAdmin', true)
        } else {
          localStorage.setItem('isAdmin', false)
        }

        commit('setPermissions', per)
        resolve(per)
      })

    })

  }
}

const mutations = {
  setUserData (state, data) {
    localStorage.setItem('account', 'admin')
    localStorage.setItem('username', 'admin')
    localStorage.setItem('token', ' data.token')
    localStorage.setItem('failure_time', Date.parse(new Date()) + 1000 * 50 * 60 * 24)
    state.account = data.username
    state.token = data.token
    state.failure_time = data.failureTime
  },
  removeUserData (state) {
    localStorage.removeItem('account')
    localStorage.removeItem('token')
    localStorage.removeItem('failure_time')
    state.account = ''
    state.token = ''
    state.failure_time = ''
  },
  setPermissions (state, permissions) {
    state.permissions = permissions
  }
}

export default {
  namespaced: true,
  state,
  actions,
  getters,
  mutations
}
