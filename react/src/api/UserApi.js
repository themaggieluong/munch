import { utilFetchWrapper } from "../services/utilFetchWrapper";
const fetchWrapper = utilFetchWrapper();

export function registerUser(userData){
    return fetchWrapper.post('/users/register',userData)
}
export function loginUser(user){
    return fetchWrapper.post('/login',user);
}
export function getUser(){
    return fetchWrapper.get('/user/current');
}
export function getUserDetail(id){    
    return fetchWrapper.get(`/user/${id}`);
}
export function saveUserDetail(user){
    return fetchWrapper.post('/user',user);
}
export function logoutUser(){
    return fetchWrapper.get('/userLogout');
}