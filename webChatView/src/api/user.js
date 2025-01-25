import request from '@/utils/request.js'

export const loginService=function(loginData){
    return request.post("/user/login",loginData)
}