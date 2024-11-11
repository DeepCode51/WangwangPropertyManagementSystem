import axios from 'axios'
// import Qs from 'qs'
import router from '@/router/index'
import store from '@/store/index'


const api = axios.create({
  baseURL: process.env.VUE_APP_API_ROOT,
  timeout: 10000,
  responseType: 'blob',
  // withCredentials: true
})

api.interceptors.request.use(
  request => {
    request.headers
    request.headers.Authorization = store.state.user.token
    return request
  }
)

api.interceptors.response.use(
  response => {
    // 如果接口请求时发现 token 失效，则立马跳转到登录页
    if (response.data.code == 401) {
      toLogin()
      return false
    }
    return Promise.resolve(response)
  },
  error => {
    return Promise.reject(error)
  }
)

export default api
