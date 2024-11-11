import axios from 'axios'
// import Qs from 'qs'
import router from '@/router/index'
import store from '@/store/index'
import { Message } from 'element-ui'

const toLogin = () => {
  console.log(1);
  router.push({
    path: '/home',
    query: {
      redirect: router.currentRoute.fullPath
    }
  })
}

const api = axios.create({
  baseURL: process.env.VUE_APP_API_ROOT,
  timeout: 10000,
  responseType: 'json',
  withCredentials: true
})

api.interceptors.request.use(
  request => {

    return request
  }
)

api.interceptors.response.use(
  response => {
    const data = response.data
    if (data.code == 403) {
      localStorage.removeItem('isLogin')
      toLogin()
    }

    return Promise.resolve(data)
  },
  error => {
    return Promise.reject(error)
  }
)

export default api
