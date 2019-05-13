import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/page/main/Main.vue'
import store from './store'
import axios from 'axios'

Vue.use(Router)
axios.defaults.withCredentials = true;
const requireAuth = () => (from, to, next) => {
  // const isAuthenticated = false
	if (store.state.ieumAccessToken) return next()
	next('/login?returnPath=me')
}

const requireAdmin = () => (from, to, next) => {
  // const isAuthenticated = false
	if (store.state.isAdmin) return next()
	alert("접근 권한이 없습니다.");
	next('/');
}

const logout = () => (from, to, next) =>{
	store.dispatch('LOGOUT');
	next('/')
}

const vueRouter = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'main',
      component: Main
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ '@/page/About.vue')
    },
    {
      path: '/greetings',
      name: 'greetings',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('@/page/Greetings.vue')
    },
    {
      path: '/directions',
      name: 'directions',
      component: () => import('@/page/Directions.vue')
    },
    {
      path: '/history',
      name: 'history',
      component: () => import('@/page/History.vue')
    },
    {
      path: '/notice',
      name: 'notice',
      component: () => import('@/page/notice/Notice.vue')
    },
    {
        path: '/notice/insert',
        name: 'noticeInsert',
        component: () => import('@/page/notice/NoticeInsert.vue'),
        afterEnter : requireAdmin()
    },
    {
        path: '/notice/:id',
        name: 'noticeDetail',
        component: () => import('@/page/notice/NoticeDetail.vue')
    },
    {
        path: '/notice/modify/:id',
        name: 'noticeModify',
        component: () => import('@/page/notice/NoticeModify.vue'),
        afterEnter : requireAdmin()
    },
    {
        path: '/freeBoard',
        name: 'FreeBoardList',
        component: () => import('@/page/freeBoard/FreeBoardList.vue')
    },
    {
        path: '/freeBoard/insert',
        name: 'FreeBoardInsert',
        component: () => import('@/page/freeBoard/FreeBoardInsert.vue')
    },
    {
        path: '/freeBoard/:id',
        name: 'FreeBoardDetail',
        component: () => import('@/page/freeBoard/FreeBoardDetail.vue')
    },
    {
        path: '/freeBoard/modify/:id',
        name: 'FreeBoardModify',
        component: () => import('@/page/freeBoard/FreeBoardModify.vue')
    },
    {
        path: '/dataRoom',
        name: 'DataRoomList',
        component: () => import('@/page/dataRoom/DataRoomList.vue')
    },
    {
        path: '/dataRoom/insert',
        name: 'DataRoomInsert',
        component: () => import('@/page/dataRoom/DataRoomInsert.vue')
    },
    {
        path: '/dataRoom/:id',
        name: 'DataRoomDetail',
        component: () => import('@/page/dataRoom/DataRoomDetail.vue')
    },
    {
        path: '/dataRoom/modify/:id',
        name: 'DataRoomModify',
        component: () => import('@/page/dataRoom/DataRoomModify.vue')
    },
    {
      path: '/activity',
      name: 'activity',
      component: () => import('@/page/activity/ActivityList.vue')
    },
    {
        path: '/activity/insert',
        name: 'activityInsert',
        component: () => import('@/page/activity/ActivityInsert.vue'),
        afterEnter : requireAdmin()
    },
    {
        path: '/activity/:id',
        name: 'activityDetail',
        component: () => import('@/page/activity/ActivityDetail.vue')
    },
    {
        path: '/activity/modify/:id',
        name: 'activityModify',
        component: () => import('@/page/activity/ActivityModify.vue'),
        afterEnter : requireAdmin()
    },
    {
	    path: '/admin/banner',
	    name: 'banner',
	    component: () => import('@/page/admin/banner/BannerList.vue'),
	    afterEnter : requireAdmin()
	},
    {
        path: '/admin/banner/modify/:id',
        name: 'bannerModify',
        component: () => import('@/page/admin/banner/BannerModify.vue'),
        afterEnter : requireAdmin()
    },
    {
	    path: '/reservation',
	    name: 'reservation',
	    component: () => import('@/page/reservation/Reservation.vue'),
	},
	{
	    path: '/admin/reservation',
	    name: 'adminReservation',
	    component: () => import('@/page/admin/reservation/ReservationMng.vue'),
        afterEnter : requireAdmin()
	},
    {
        path: '/admin/program',
        name: 'adminProgram',
        component: () => import('@/page/admin/program/ProgramList.vue'),
        afterEnter : requireAdmin()
    },
    {
        path: '/admin/program/insert',
        name: 'adminProgramInsert',
        component: () => import('@/page/admin/program/ProgramInsert.vue'),
        afterEnter : requireAdmin()
    },
    {
      path: '/admin/program/:id',
      name: 'adminProgramDetail',
      component: () => import('@/page/admin/program/ProgramDetail.vue'),
      afterEnter : requireAdmin()
    },
    {
      path: '/admin/program/modify/:id',
      name: 'adminProgramModify',
      component: () => import('@/page/admin/program/ProgramModify.vue'),
      afterEnter : requireAdmin()
    },
    {
      path: '/program/:kind',
      name: 'programList',
      props : true,
      component: () => import('@/page/program/ProgramList.vue'),
    },
    {
      path: '/program/:kind/:id',
      name: 'programDetail',
      props : true,
      component: () => import('@/page/program/ProgramDetail.vue'),
    },
    {
      path: '/program/:kind/application/:id',
      name: 'programApplication',
      props : true,
      component: () => import('@/page/program/ProgramApplication.vue'),
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/page/Login.vue')
    },
    {
        path: '/logout',
        name: 'logout',
        beforeEnter: logout()
      }
  ]
});

function level(m, url){
	let level = [];
	if(!url) return level;
	(function find(m){
		return m.some(function(cm){
			level[cm.level] = cm;
			if(cm.url==url){
				return true;
			}else{
				if(cm.menuList.length > 0){
					return find(cm.menuList);
				}
			}
		});
	})(m);
	
	return level;
}

vueRouter.beforeEach((to, from, next) => {
  // to : 대상 Route 객체 로 이동합니다.
  // from : 현재 라우트로 오기전 라우트 입니다.
  // next : to에서 지정한 url로 이동하기 위해 꼭 호출해야 하는 함수.
//  window.console.log("라우터 변경이 일어날 때 전처리 하는 부분 입니다.");
  
  
//  axios.get(store.getters.restWebPath+"/refresh",{responseType: 'json'})
  
  next();
  
});

vueRouter.afterEach((to, from) => {
  // ...후
//  window.console.log("라우터 변경이 일어날 때 후처리 하는 부분 입니다."+from);
  
  axios.get(store.getters.restWebPath+"/roleState",{params:{url:to.matched[0].path},responseType: 'json'})
	.then((respons) =>{
		const data = respons.data;
		store.dispatch('LOGINCHECK', {session:data})
	});
  
  store.state.menuLevel = level(store.state.menu,to.matched[0].path);
  
})

export default vueRouter
