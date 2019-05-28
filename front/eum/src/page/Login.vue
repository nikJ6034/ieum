<template>

  <section class="login">
<!--    <div>-->
      <!--<h2>Login</h2>-->
<!--       <form @submit.prevent="onSubmit(memberName, memberPassword)"> -->
<!--         <input type="text" v-model="memberName" placeholder="ID"> -->
<!--         <input type="password" v-model="memberPassword" placeholder="PASSWORD"> -->
<!--         <input type="submit" value="Login"> -->
<!--       </form> -->
      
      <!--<a href="#" id="kakao-login-btn"></a>-->
<!--      <a @click="loginPro"><img :src="require('@/assets/images/kakao/kakao_account_login_btn_medium_narrow.png')"/></a>-->
<!--    </div>-->
    <div id="content">
      <div id="con">
        <div class="login-wrap">
          <div class="login-hd">
            <h4><strong>이음마을학교</strong>에 오신것을 환영합니다.</h4>
            <p>아이디와 비번을 입력하시면 다양한 서비스를 이용하실 수 있습니다.</p>
          </div>
          <div class="login-con">
            <!--<div class="login-form">

              <div class="login-box">
                <ul class="login-list ">
                  <li>
                    <label class="login-label">아이디</label>
                    <div>
                      <input type="text" name="ur_userid" class="intxt" value="">
                    </div>
                  </li>
                  <li></li>
                  <li>
                    <label class="login-label">비밀번호</label>
                    <div>
                      <input type="password" class="intxt" name="ur_password">
                    </div>
                  </li>

                </ul>
                <input class="login-btn login" type="button" value="로그인">
              </div>
            </div>-->

            <div class="kakao">
              <a @click="loginPro"><img :src="require('@/assets/images/sub/kakao_login.jpg')" alt="kakao" /></a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

</template>

<script lang="js">
// import "@/assets/js/kakao.js";
  export default  {
    name: 'login',
    props: [],
    mounted() {
    	let _this = this;
        // 카카오 로그인 버튼을 생성합니다.
       /* Kakao.Auth.createLoginButton({
          container: '#kakao-login-btn',
          success: function(authObj) {
        	  _this.$store.dispatch('LOGIN', {memberName:authObj.access_token, password:""})
              .then(() => _this.redirect())
              .catch(({message}) => _this.msg = message);
          },
          fail: function(err) {
        	  alert("로그인에 실패하였습니다.");
          }
        });*/
    },
    data() {
      return {
      }
    },
    methods: {
//       onSubmit(memberName, memberPassword) {
        // LOGIN 액션 실행
//         this.$store.dispatch('LOGIN', {memberName, memberPassword})
//           .then(() => this.redirect())
//           .catch(({message}) => this.msg = message)
//       },
      redirect() {
        this.$router.push('/')
      },
      loginPro : function(){
        let _this = this;
        Kakao.Auth.loginForm({success: function(authObj) {
            _this.$store.dispatch('LOGIN', {memberName:authObj.access_token, password:"", kind : "kakao"});
                    //.then(() => _this.redirect())
                    //.catch(({message}) => _this.msg = message);
          },
          fail: function() {
            alert("로그인에 실패하였습니다.");
          }})
      }
    },
    computed: {

    }
}
</script>

<style scoped lang="scss">
  .login {

  }
</style>
<style scoped src="@/assets/css/custom.css"></style>