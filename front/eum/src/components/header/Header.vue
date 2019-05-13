<template>
    <section class="header">
        <!-- Header with Background Image -->
        <div class="header-container">
            <b-nav class="tnbNav">
                <b-nav-item active to="/">홈</b-nav-item>
                <template  v-if="!store.state.ieumAccessToken">
                    <b-nav-item to='/login'>로그인</b-nav-item>
                </template >
                <template  v-else>
                    <b-nav-item><template v-if="store.state.ieumUserName">{{store.state.ieumUserName}}님 환영합니다.</template></b-nav-item>
                    <b-nav-item to="/logout">로그아웃</b-nav-item>
                </template >
                <b-nav-item>회원가입</b-nav-item>
                <b-nav-item to="/about">사이트맵</b-nav-item>
            </b-nav>
        </div>
    </section>
</template>

<script lang="js">
export default  {
    name: 'Header',
    props: [],
    mounted() {

    },
    data() {
        return {

        }
    },
    methods: {

    },
    computed: {
    }
}
</script>

<style scoped lang="scss">
    .header {
        background: #171d33;
        height: 40px;
    }

    .header .header-container{
        position: relative;
        padding: 0;
        margin:auto;
    }

    .header .header-container .tnbNav {
        float: right;
    }
    .header .header-container .tnbNav ul{
        position: relative;
        float: left;
    }
    .header .header-container .tnbNav li{
        margin: 0;
        position: relative;
        float: left;
    }

    .header .header-container .tnbNav a{
        color: #ffffff;
        font-size: 13px;
        padding: 10px 12px;
        display: inline-block;
    }

    .header .header-container .tnbNav li > a::before{
        position: absolute;
        left: 0;
        content: '|';
        color: #929294;
    }

    .header .header-container .tnbNav li:first-child > a::before{
        content: '';
    }

  

  /* Large desktops and laptops */
@media (min-width: 1200px) {
    .header .header-container{
        width: 1170px;
    }
}

/* Landscape tablets and medium desktops */
@media (min-width: 992px) and (max-width: 1199px) {

}

/* Portrait tablets and small desktops */
@media (min-width: 768px) and (max-width: 991px) {
    .header {
        display: none;
    }
}

/* Landscape phones and portrait tablets */
@media (max-width: 767px) {
    .header {
        display: none;
    }
}

/* Portrait phones and smaller */
@media (max-width: 480px) {
    .header {
        display: none;
    }
}
</style>
