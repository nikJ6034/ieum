import $http from "axios";
import store from "../store";

export default {
    loginCheck : function(callback){
        const {ieumAccessToken} = localStorage;
        if(ieumAccessToken){
            $http.get(`${store.state.basePath}/oauth/check_token?token=${ieumAccessToken}`)
                .then(({data})=>{
                    //토큰인증이 되면 브라우저 내에 있는 토큰정보를 설정한다.
                    $http.defaults.headers.common['Authorization'] = "Bearer "+ieumAccessToken;
                    store.state.ieumAccessToken = ieumAccessToken;
                    store.state.ieumUserName = data.name;
                    store.state.ieumUserId = data.id;
                })
                .catch(error=>{
                    if(error.response.data.error === "invalid_token"){
                        //토큰 만료시 브라우저 내에있는 토큰값을 삭제한다.
                        delete $http.defaults.headers.common['Authorization'];
                        store.state.ieumAccessToken = "";
                        store.state.ieumUserName = "";
                        store.state.ieumUserId = "";
                    }
                })
                .finally(()=>{
                    callback();
                })
        }else{
            callback();
        }
    }
}