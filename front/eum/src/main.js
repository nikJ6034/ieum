import Vue from 'vue'
import BootstrapVue from 'bootstrap-vue'
import App from './App.vue'
import axios from 'axios'
import router from './router'
import store from './store'
import moment from 'moment'

//import 'expose-loader?$!expose-loader?jQuery!jquery'
import jquery from 'jquery';
global.jQuery = require('jquery');
var $ = global.jQuery;
window.$ = jquery;
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import './assets/css/global.css';

Vue.use(BootstrapVue);

Vue.config.productionTip = false;
Vue.prototype.$http = axios; // add
Vue.prototype.$ = jquery;
Vue.prototype.store = store;
Vue.prototype.router = router;

Vue.filter('formatDate', function(value) {
  if (value) {
    return moment(String(value)).format('YYYY-MM-DD HH:mm');
  }
});

Vue.filter('formatDateKo', function(value) {
	  if (value) {
	    return moment(String(value)).format('YYYY년MM월DD일 HH시mm분');
	  }
	});

Vue.filter('formatDateKo2', function(value) {
    if (value) {
        return moment(String(value)).format('YYYY년MM월DD일');
    }
});

import FullCalendar from 'vue-full-calendar'
Vue.use(FullCalendar)

//새로고침 시 로컬 리포지토리에 토큰이 있는 경우 토큰을 넣어준다.
const enhanceAccessToeken = () => {
    const {ieumAccessToken} = localStorage
    const {ieumUserName} = localStorage;

  const {userid} = localStorage;
    if (ieumAccessToken) {
    axios.defaults.headers.common['Authorization'] = "Bearer "+ieumAccessToken;
    store.state.ieumAccessToken = ieumAccessToken;
    store.state.ieumUserName = ieumUserName;
    store.state.ieumUserId = userid;
    }else{
        delete axios.defaults.headers.common['Authorization'];
        store.state.ieumAccessToken = "";
        store.state.ieumUserName = "";
        store.state.ieumUserId = "";
    }

}

axios.get(`${store.state.basePath}/menu`)
.then(({data})=>{
	store.commit('MENU',data)
}).then(function(){
	
	new Vue({
          store,
          router,
          axios,
          render: h => h(App)
		}).$mount('#app')

    enhanceAccessToeken();
})



