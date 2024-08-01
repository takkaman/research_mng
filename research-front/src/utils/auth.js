import Cookies from 'js-cookie'

const TokenKey = 'vue_admin_template_token'
const userIdKey = 'userId'
const userNameKey = 'userName'
const userTypeKey = 'userType'
export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
//存储用户id
export function setUserId(userId){
  return sessionStorage.setItem(userIdKey,userId)
}
//获取用户id
export function getUserId(){
  return sessionStorage.getItem(userIdKey)
}
export function removeUserId() {
    return sessionStorage.removeItem(userIdKey);
  }
//存储用户id
export function setUserName(userName){
    return sessionStorage.setItem(userNameKey,userName)
}
//获取用户id
export function getUserName(){
    return sessionStorage.getItem(userNameKey)
}
export function removeUserName() {
  return sessionStorage.removeItem(userNameKey);
}
//清空sesssion
export function clearSession(){
  return sessionStorage.clear()
}
export function getUserType() {
  return Cookies.get(userTypeKey)
}

export function setUserType(userType) {
  return Cookies.set(userTypeKey, userType)
}
