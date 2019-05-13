import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import qs from 'qs';

Vue.use(Vuex)
//axios.create({'Access-Control-Allow-Origin': "*", 'Access-Control-Allow-Credentials': true,'Access-Control-Allow-Headers':'Origin, X-Requested-With, Content-Type, Accept'});
axios.defaults.withCredentials = true;
export default new Vuex.Store({
  state: {
	basePath : "http://localhost:8080",
	ieumUserName: null,
	ieumUserId: null,
    ieumAccessToken: null,
    isAdmin: false,
    menuRole: {},
    menu: {},
    menuLevel : []
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
        localStorage.ieumAccessToken = session["access_token"];
        localStorage.ieumUserName = session["name"];
        state.ieumAccessToken = session["access_token"];
        state.ieumUserName = session["name"];
        axios.defaults.headers.common['Authorization'] = "Bearer "+session["access_token"];
        axios.get(this.state.basePath+"/api/web/roleState",{params:{url:"/"},responseType: 'json'})
        .then((respons) =>{
            const data = respons.data;
            this.dispatch('LOGINCHECK', {session:data})
        });
    },
    LOGOUT (state) {
        state.ieumAccessToken = null;
        state.ieumUserName = null;
        state.isAdmin = false;
        delete axios.defaults.headers.common["Authorization"];
        delete localStorage.ieumAccessToken;
    },
    LOGINCHECK (state, data){
      if(data){
        state.isAdmin = data.isAdmin||false;
        state.menuRole = data.menuRole;
        state.ieumUserId = data.id;
      }
    },
    MENU(state, data){
        state.menu = data;
    }
  },
  actions: {
    LOGIN ({commit}, {memberName, memberPassword}) {
    
//    const data = {"grant_type":"password","scope":"read","client_id":"client1", "client_secret":"1234"};
//      return axios.post(`${this.getters.restWebPath}/login`, {memberName,memberPassword},{headers:{'Access-Control-Allow-Origin': "*"}})
//      .then((response) => {
//    	  commit('LOGIN', response.headers)
//      });s
    	
    	let str = qs.stringify({
    		grant_type: 'password',
          username: memberName,
          password: memberPassword,
          scope: 'openid'
    	});
    	
    	axios.request({
    		  url: `/oauth/token`,
    		  method: "post",
    		  baseURL: this.state.basePath,
    		  auth: {
    		    username: "client1", // This is the client_id
    		    password: "1234", // This is the client_secret
    		  },
    		  data: str,
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			}
    		}).then(respose => {
    			commit('LOGIN', respose.data); 
    		});
      
    },
    LOGOUT ({commit}) {
    
      axios.get(`${this.getters.restWebPath}/logout`,{headers:{'Access-Control-Allow-Origin': "*"}})
      .then(()=>{commit('LOGOUT');})
      
    },
    LOGINCHECK ({commit}, {session}){
      if(session != null){
        if(session.id){
            commit('LOGINCHECK', session);
        }else{
            commit('LOGOUT');
        }
      }else{
        commit('LOGOUT');
      }
      
    }
  }
})
