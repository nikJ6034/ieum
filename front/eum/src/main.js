import Vue from 'vue'
import BootstrapVue from 'bootstrap-vue'
import App from './App.vue'
import $http from 'axios'
import router from './router'
import store from './store'
import dateFormat from './filter/DateFormat'
import auth from './modules/auth';
import jquery from 'jquery';
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import './assets/css/global.css';
import FullCalendar from 'vue-full-calendar'

global.jQuery = require('jquery');
Vue.use(BootstrapVue);
window.$ = jquery;
let $ = global.jQuery;
Vue.config.productionTip = false;
Vue.prototype.$http = $http; // add
Vue.prototype.$ = jquery;
Vue.prototype.store = store;
Vue.prototype.router = router;
Vue.use(FullCalendar)

dateFormat.format(); // 날짜 포맷 필터

$http.get(`${store.state.basePath}/menu`)
.then(({data})=>{
    store.commit('MENU',data)
}).then(function(){
    //새로고침 시 로컬 리포지토리에 토큰이 있는 경우 토큰을 넣어준다.
    auth.loginCheck(()=>{
        new Vue({
            store,
            router,
            $http,
            render: h => h(App)
        }).$mount('#app')
    });
})



