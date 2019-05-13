<template lang="contactlist-component">

  <section class="login">
    <div>
      <h2>Login</h2>
<!--       <form @submit.prevent="onSubmit(memberName, memberPassword)"> -->
<!--         <input type="text" v-model="memberName" placeholder="ID"> -->
<!--         <input type="password" v-model="memberPassword" placeholder="PASSWORD"> -->
<!--         <input type="submit" value="Login"> -->
<!--       </form> -->
      
      <a href="#" id="kakao-login-btn"></a>
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
        Kakao.Auth.createLoginButton({
          container: '#kakao-login-btn',
          success: function(authObj) {
        	  _this.$store.dispatch('LOGIN', {memberName:authObj.access_token, password:""})
              .then(() => _this.redirect())
              .catch(({message}) => _this.msg = message);
          },
          fail: function(err) {
        	  alert("로그인에 실패하였습니다.");
          }
        });
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

//         리다이렉트 처리
        this.$router.push('/')
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
