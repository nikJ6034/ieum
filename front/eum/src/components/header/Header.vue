<template>
    <header class="header" id="header">
        <!-- Header with Background Image -->
        <div class="top-gnb">
            <div class="con-area">
                <ul>
<!--                    <li><a href="#" class="admin">관리자메뉴</a></li>-->
                    <template  v-if="!store.state.ieumAccessToken">
                        <li><router-link to='/login'>로그인</router-link></li>
                        <li><router-link to='/login'>회원가입</router-link></li>
                    </template>
                    <template  v-else>
                        <li class="user">{{store.state.ieumUserName}}님 환영합니다.</li>
                        <li><router-link to="/logout">로그아웃</router-link></li>
                    </template>
                </ul>
            </div>
        </div>

        <div class="gnb-area">
            <div class="con-area">
                <h1 id="logo">
                    <router-link to="/">
                        <img :src="require('@/assets/images/common/logo.png')"  alt="이음마을학교" />
                    </router-link>
                </h1>
                <nav>
                    <div class="menu-tit">
                        <strong class="tit"><span>학생중심</span>의 사랑과<br/>
                            자발성 교육</strong>
                        <p><strong>이음 마을 학교</strong>입니다.</p>
                    </div>
                    <ul class="menu">
						<template v-if="store.state.menu[0]">
							<li v-for="menu in store.state.menu" v-bind:key="menu.id">
								<template v-if="menu.menuName !='관리자 메뉴' || (menu.menuName =='관리자 메뉴' && store.state.isAdmin)">
									<a href="#" :class="active(menu.id)"><span>{{menu.menuName}}</span></a>
									<ul class="snb1">
										<template v-if="menu.menuList">
											<li v-for="chMenu in menu.menuList" v-bind:key="chMenu.id">
												<router-link :to="chMenu.url||''" >{{chMenu.menuName}}</router-link>
											</li>
										</template>
									</ul>
								</template>
							</li>
                        </template>
                    </ul>
                </nav>
                <ul class="top-menu">
<!--                    <li><a href="#">회원가입</a></li>-->
                    <li><a href="#" class="sitemap"><img :src="require('@/assets/images/common/sitemap_icon.png')" alt="이음마을학교" /></a></li>
                </ul>
            </div>
        </div>

        <div class="mo-header">
            <div class="h-box">
                <router-link to="/">
                    <h1 id="mo-logo">
                        <img :src="require('@/assets/images/common/mo_logo.png')"  alt="이음마을학교" />
                    </h1>
                </router-link>

                <span class="mo-menu-btn"></span>
            </div>

            <div class="mo-gnb-wrap">
                <div class="mo-menu">
                    <div class="con-box">
                        <ul class="mo-gnb" id="moMenu">
							<template v-if="store.state.menu[0]">
								<li :class="active(menu.id)"  v-for="menu in store.state.menu" v-bind:key="menu.id">
									<a href="#" class="active(menu.id)"><span>{{menu.menuName}}</span></a>
									<ul>
										<template v-if="menu.menuList">
											<li v-for="chMenu in menu.menuList" v-bind:key="chMenu.id"><router-link :to="chMenu.url||''" >{{chMenu.menuName}}</router-link></li>
										</template>
									</ul>
								</li>
							</template>
                            <li v-if="store.state.isAdmin">
                                <!-- 관리자 메뉴  -->
                                <a  href="#">관리자 메뉴</a>
                                <ul>
                                    <li >
                                        <router-link to="/admin/banner" >배너 관리</router-link>
                                    </li>
                                    <li >
                                        <router-link to="/admin/reservation" >대관신청 관리</router-link>
                                    </li>
                                    <li >
                                        <router-link to="/admin/program" >프로그램 관리</router-link>
                                    </li>
                                </ul>
                            </li>
                        </ul>

                        <div class="member-box">
                            <ul>
                                <template  v-if="!store.state.ieumAccessToken">
                                    <li><router-link to='/login'>로그인</router-link></li>
                                </template>
                                <template  v-else>
                                    <li>{{store.state.ieumUserName}}님 환영합니다.</li>
                                    <li><router-link to="/logout">로그아웃</router-link></li>
                                </template>
                            </ul>
                        </div>

                    </div>
                </div>

                <div class="bodyBg"></div>
            </div>
        </div>
    </header>
</template>

<script lang="js">
     import menu from "@/assets/js/menu";
    export default  {
    name: 'Header',
    props: [],
    mounted() {
        menu.fullMenu(".menu > li",".menu > li  > a",".menu > li > .snb1",".gnb-area");
        menu.mobileMenu();
        menu.accordionMenu("#moMenu > li", "#moMenu > li.open");
    },
    data() {
        return {

        }
    },
    methods: {
        active : function(id){
            let topLevel = this.store.state.menuLevel[0] || null;

            if(!topLevel){
                return "";
            }
            if(id == topLevel.id){
                return "active";
            }
            return "";
        }
    },
    computed: {
    }
}
</script>
