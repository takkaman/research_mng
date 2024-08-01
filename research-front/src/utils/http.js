import axios from 'axios'
import qs from 'qs'
import {Message } from 'element-ui'

const service = axios.create({
    baseURL: 'http://127.0.0.1:8088', // url = base url + request url
    // withCredentials: true, // send cookies when cross-domain requests
    timeout: 5000 // request timeout
  })

// 请求拦截器
service.interceptors.request.use(
    config => {
      // 可以在这里添加请求头等信息
      // if (store.getters.token) {
      //   config.headers['X-Token'] = getToken();
      // }
      return config;
    },
    error => {
      // 请求错误处理
      console.log(error); // for debug
      Promise.reject(error);
    }
  );

//请求返回之后的处理
service.interceptors.response.use(
    /**
     * If you want to get http information such as headers or status
     * Please return  response => response
    */
  
    /**
     * Determine the request status by custom code
     * Here is just an example
     * You can also judge the status by HTTP Status Code
     */
    response => {
      const res = response.data  
      // if the custom code is not 20000, it is judged as an error.
      if (res.code !== 200) {
        Message({
          message: res.msg || '服务器出错',
          type: 'error',
          duration: 5 * 1000
        })
        return Promise.reject(new Error(res.msg || '服务器出错'))
      } else {
        return res
      }
    },
    error => {
      console.log('err' + error) // for debug
      Message({
        message: error.msg || '服务器出错!',
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(error)
    }
  )

//请求方法
const http = {
    post(url, params) {
      return service.post(url, params, {
        transformRequest: [(params) => {
          return JSON.stringify(params)
        }],
        headers: {
          'Content-Type': 'application/json'
        }
      })
    },

    mpPost(url, params) {
      return service.post(url, params, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
    },
    
    get(url, params) {
      return service.get(url, {
        params: params,
        paramsSerializer: (params) => {
          return qs.stringify(params)
        }
      })
    },
  }
  export default http