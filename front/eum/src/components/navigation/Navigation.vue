<template lang="contactlist-component">
    <section class="navigation">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-4 d-none d-lg-block text-center my20"><router-link to="/" ><img :src="logo" class="w200"/></router-link></div>
                    <div class="col-12 col-lg-8">
                        <nav>
                           <div class="nav-xbootstrap my20">
                                <ul>
                                    <li v-if="store.state.menu[0]" v-for="menu in store.state.menu">
                                        <a  :style="active(menu.id)"  href="#">{{menu.menuName}}<span class="glyphicon glyphicon-chevron-down iconsize"></span></a>
                                        <ul class="dropdown">
                                            <li v-if="menu.menuList" v-for="chMenu in menu.menuList">
                                                <router-link :to="chMenu.url||''" >{{chMenu.menuName}}</router-link>
                                            </li>
                                        </ul>
                                    </li>
                                    <li v-if="store.state.isAdmin">
                                        <!-- 관리자 메뉴  -->
                                        <a  href="#">관리자 메뉴<span class="glyphicon glyphicon-chevron-down iconsize"></span></a>
                                        <ul class="dropdown">
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
                            <div class="row d-block d-lg-none">
                                <div class="col text-center"><b-nav-item to='/login'>로그인</b-nav-item></div>
                            </div>
                         </div>
                        <div class="nav-bg-xbootstrap">
                            <div class="navbar-xbootstrap"> <span></span> <span></span> <span></span> </div>
                            <div class="title-mobile"><router-link to="/">마을학교 이음</router-link></div>
                            <!-- <a href="#" >마을학교 이음</a> -->
                        </div>
                     </nav>
                </div>
            </div>
         </div>
       </section>
</template>

<script lang="js">
  export default  {
    name: 'navigation',
    props: [],
    mounted() {
     this.$(($)=>{
      $('.navbar-xbootstrap').click(function(){
        $('.nav-xbootstrap').toggleClass('visible');
        // $('body').toggleClass('cover-bg');
      });

      function initMunu(){
        const navLi = $(".nav-xbootstrap li");
        navLi.removeClass("active");
        navLi.children("a").removeClass("show");
        navLi.children("ul.dropdown").removeClass("show");
      }

       $(window).resize(function () {
            if(window.innerWidth > 991){
              initMunu();
            }
        });

      $(".nav-xbootstrap li").click(function(){
        if(window.innerWidth <= 991){
          initMunu();

          if(!$(this).hasClass("active")){
            $(this).addClass("active");
            $(this).children("a").addClass("show");
            $(this).children("ul.dropdown").addClass("show");
          }
        }
        
      })
     })
    },
    data() {
      return {
        logo : require('@/assets/img/logo.png')
      }
    },
    methods: {
    	active : function(id){
    		let topLevel = this.store.state.menuLevel[0] || null;
    		
    		if(!topLevel){
    			return "";
    		}
    		if(id == topLevel.id){
    			return {'background-color':'#93a0ba','color':'#ffffff'};
    		}
    		return "";
    	}
    },
    computed: {
      
    }
}
</script>

<style scoped lang="scss">
  .navigation {

  }

.nav-xbootstrap {
  display: block;
  margin-bottom: 15px 0;
  // background: #03A9F4;
  // -webkit-box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.26);
  // -moz-box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.26);
  // -ms-box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.26);
  // -o-box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.26);
  // box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.26);
  // border-radius: 3px;
  // height: 100px;
}
.iconsize {
    font-size: 12px; 
    top: 2px; 
    left: 3px;
}

.nav-xbootstrap ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  display: block;
  z-index: 10;
}

.nav-xbootstrap li {
  list-style-type: none;
  margin: 0;
  padding: 0;
  display: inline-block;
  position: relative;
  font-size: 14;
  color: #def1f0;
}

.nav-xbootstrap li a {
  padding: 15px 20px;
  font-size: 18;
  color: #000000;
  display: inline-block;
  outline: 0;
  font-weight: 600;
  text-decoration: none;
}

.nav-xbootstrap li ul.dropdown {
  position: absolute;
  display: none;
  width: 200px;
  background: #606677;
  -webkit-box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.26);
  -moz-box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.26);
  -ms-box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.26);
  -o-box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.26);
  box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.26);
  padding-top: 0;
}

.nav-xbootstrap li ul.dropdown li {
  display: block;
  list-style-type: none;
}

.nav-xbootstrap li ul.dropdown li a {
  padding: 15px 20px;
  font-size: 15px;
  color: #fff;
  display: block;
  font-weight: 400;
}

.nav-xbootstrap li ul.dropdown li:last-child a { border-bottom: none; }

// .nav-xbootstrap li:first-child:hover a { border-radius: 3px 0 0 3px; }

.nav-xbootstrap li ul.dropdown li:hover a { background: rgba(0,0,0, .1); }

// .nav-xbootstrap li ul.dropdown li:first-child:hover a { border-radius: 0; }

.nav-xbootstrap li:hover .arrow-down { border-top: 5px solid #fff; }

.arrow-down {
  width: 0;
  height: 0;
  border-left: 5px solid transparent;
  border-right: 5px solid transparent;
  border-top: 5px solid #def1f0;
  position: relative;
  top: 15px;
  right: -5px;
  content: '';
}
.title-mobile {
  display: none;
}

 @media only screen and (min-width:992px){
   .nav-xbootstrap li:hover a {
    background: #31353d;
    color: #fff !important;
  }

   .nav-xbootstrap li:hover ul.dropdown { 
      display: block; 
  } 
 }

 @media only screen and (max-width:991px) {

   .nav-xbootstrap li ul.dropdown.show { 
      display: block; 
  } 

  .navigation .container-fluid{
    height: 50px;
    // margin-bottom: 15px;
  }

.nav-xbootstrap {
  background: #b4b5af;
  width: 200px;
  height: 100%;
  display: block;
  position: relative;
  left: -215px;
  top: 50px;
  z-index: 20;
  -webkit-transition: left 0.25s ease;
  -moz-transition: left 0.25s ease;
  -ms-transition: left 0.25s ease;
  -o-transition: left 0.25s ease;
  transition: left 0.25s ease;
  margin: 0;
  border: 0;
  border-radius: 0;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
}
.title-mobile {
  // position: fixed;
  display: block;
    top: 10px;
    font-size: 20px;
    left: 100px;
    right: 100px;
    text-align: center;
    color: #FFF;
}
.title-mobile a{
  color: #FFF;
  text-decoration:none !important
}
.nav-xbootstrap.visible {
  left: -15px;
  -webkit-transition: left 0.25s ease;
  -moz-transition: left 0.25s ease;
  -ms-transition: left 0.25s ease;
  -o-transition: left 0.25s ease;
  transition: left 0.25s ease;
}

.nav-bg-xbootstrap {
  display: inline-block;
  vertical-align: middle;
  width: 100%;
  height: 50px;
  margin: 0;
  position: absolute;
  top: 0px;
  left: 0px;
  background: #31353d;
  padding: 12px 0 0 10px;
  -webkit-box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.26);
  -moz-box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.26);
  -ms-box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.26);
  -o-box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.26);
  box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.26);
}

.navbar-xbootstrap {
  display: inline-block;
  vertical-align: middle;
  height: 50px;
  cursor: pointer;
  margin: 0;
    position: absolute;
    top: 0;
    left: 0;
    padding: 12px;
}

.navbar-xbootstrap span {
  height: 2px;
  background: #fff;
  margin: 5px;
  display: block;
  width: 20px;
}

.navbar-xbootstrap span:nth-child(2) { width: 20px; }

.navbar-xbootstrap span:nth-child(3) { width: 20px; }

// .nav-xbootstrap ul { padding-top: 50px; }

.nav-xbootstrap li { display: block; }

.nav-xbootstrap li a {
  display: block;
  color: #505050;
  font-weight: 600;
}

.nav-xbootstrap li:first-child:hover a { border-radius: 0; }

.nav-xbootstrap li ul.dropdown { position: relative; }

.nav-xbootstrap li ul.dropdown li a {
  background: #606677 !important;
  border-bottom: none;
  color: #fff !important;
}

// .nav-xbootstrap li:hover a {
//   background: #03A9F4;
//   color: #fff !important;
// }

.nav-xbootstrap li a.show {
  background: #31353d;
  color: #fff !important;
}

.nav-xbootstrap li ul.dropdown li:hover a {
  background: rgba(0,0,0,.1);
  color: #fff !important;
}

.nav-xbootstrap li ul.dropdown li a { padding: 10px 10px 10px 50px; }

.nav-xbootstrap li:hover .arrow-down { border-top: 5px solid #fff; }

.arrow-down {
  border-top: 5px solid #505050;
  position: absolute;
  top: 20px;
  right: 10px;
}

.cover-bg {
  background: rgba(0,0,0,0.5);
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
}
 }
 @media only screen and (max-width:1199px) {

.container { width: 96%; }


.fixed-top {
  position: fixed;
  top: 0;
  right: 0;
  left: 0;
}
 }
</style>
