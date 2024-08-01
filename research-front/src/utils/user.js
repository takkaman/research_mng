import http from '@/utils/http'

//获取用户列表
export const getListApi = async(parm) =>{
  return await http.get("/api/user/list",parm)
}
//新增
export const addUserApi = async(parm) =>{
  return await http.post("/api/user",parm)
}
//编辑
export const editUserApi = async(parm) =>{
  return await http.put("/api/user",parm)
}
//删除
export const deleteUserApi = async(parm) =>{
  return await http.delete("/api/user",parm)
}
//角色列表
export const getRoleListApi = async()=>{
  return await http.get("/api/user/roleList")
}
//获取角色
export const getRoleApi = async(parm)=>{
  return await http.get("/api/user/role",parm)
}
//登录
export const loginApi = async(parm)=>{
  return await http.post("/login",parm)
}
