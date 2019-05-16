import $http from "axios";
import store from "../store";

export default {
    loginCheck : function(callback){
        const {ieumRefreshToken} = localStorage;
        if(ieumRefreshToken){
            store.dispatch('REFRESHTOKEN',localStorage.ieumRefreshToken)
            .catch(()=>{
                store.dispatch('LOGOUT'); //토큰만료시 로그아웃을 한번 해준다.
            }).finally(()=>{
                callback();
            })
        }else{
            callback();
        }
    },
    tokenCheck : async function(ieumRefreshToken){
       await $http.get(`${store.state.basePath}/oauth/check_token?token=${ieumRefreshToken}`);
    }
}