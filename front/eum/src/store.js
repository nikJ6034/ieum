import Vue from 'vue'
import Vuex from 'vuex'
import $http from 'axios'
import $qs from 'qs';
import router from './router'

Vue.use(Vuex)
$http.defaults.withCredentials = true;
export default new Vuex.Store({
  state: {
	basePath : "http://ieumschool.com",
    ieumAccessToken: null,
    ieumUserId : null,
    ieumUserName : null,
    isAdmin: false,
    menuRole: {},
    menu: {},
    menuLevel : [],
    kind : null,
    token : null,
  },
  getters: {
    restWebPath : state=>{
        return `${state.basePath}/api/web`;
      },
    restAdminPath : state=>{
        return `${state.basePath}/api/admin`;
      }
  },
  mutations: {
    LOGIN (state, session) {
        localStorage.ieumRefreshToken = session["refresh_token"];

        state.ieumAccessToken = session["access_token"];
        state.ieumUserName = session["name"];
        state.ieumUserId = session["id"];
        $http.defaults.headers.common['Authorization'] = "Bearer "+session["access_token"];
    },
    LOGOUT (state) {
        state.ieumAccessToken = null;
        state.ieumUserName = null;
        state.ieumUserId = null;
        state.isAdmin = false;
        delete $http.defaults.headers.common["Authorization"];
        delete localStorage.ieumRefreshToken;
        if(Kakao){
            Kakao.Auth.logout();
        }
    },
    LOGINCHECK (state, data){
      if(data){
        state.isAdmin = data.isAdmin||false;
        state.menuRole = data.menuRole;
      }
    },
    MENU(state, data) {
        state.menu = data;
    },
    SIGNUPREADY(state, data){
        state.kind = data.kind;
        state.token = data.token;
    }
  },
  actions: {
      LOGIN: async function ({commit}, {memberName, memberPassword, kind}) {

          let str = $qs.stringify({
              grant_type: 'password',
              username: memberName,
              password: memberPassword,
              scope: 'openid',
              kind : kind
          });
          let result = null;
          try{
              result = await $http.request({
                  url: `/oauth/token`,
                  method: "post",
                  baseURL: this.state.basePath,
                  auth: {
                      username: "ieumschool", // This is the client_id
                      password: "ieumschool2019", // This is the client_secret
                  },
                  data: str,
                  headers: {
                      'Content-Type': 'application/x-www-form-urlencoded'
                  }
              });
              commit('LOGIN', result.data);
              router.push('/');
          }catch (error) {
              if(error.response.data.error == "invalid_grant"){
                  let data = {};
                  data.token = memberName;
                  data.kind = kind;
                  commit('SIGNUPREADY', data);
                  router.push('/signup');
              }
          }
      },
    REFRESHTOKEN : async function({commit},refresh_token){
        let str = $qs.stringify({
            "grant_type":"refresh_token",
            "refresh_token":refresh_token
        });
        let {data} = await $http.request({
            url: `/oauth/token`,
            method: "post",
            baseURL: this.state.basePath,
            auth: {
                username: "ieumschool", // This is the client_id
                password: "ieumschool2019", // This is the client_secret
            },
            data: str,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });
        commit('LOGIN', data);
    },
    LOGOUT ({commit}) {
        commit('LOGOUT');
    },
    LOGINCHECK ({commit}, {session}){
        commit('LOGINCHECK', session);
    }
  }
})
