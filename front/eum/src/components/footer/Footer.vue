<template>
    <footer class="footer" id="footer">
        <div class="foot-menu">
            <div class="con-area">
                <ul>
                    <li><a href="#">공지사항</a></li>
                    <li><a href="#">이용약관</a></li>
                    <li><a href="#">개인정보처리방침</a></li>
                </ul>
                <span class="top-up-btn">TOP</span>
            </div>
        </div>
        <div class="footer-con">
            <div class="con-area">
                <div class="foot-logo">
                    <img :src="require('@/assets/images/common/foot_logo.png')" alt="이음마을학교" />
                </div>
                <div class="con-box">
                    <p class="address">대전광역시 대덕구 계족산로5번길 82 1층(법동) 이음마을학교협동조합<br/>
                        TEL : 042-334-1113   I   E-mail : ieumschool@gmail.com
                    </p>
                    <p class="copyright">COPYRIGHT 2019.  ALL RIGHTS RESERVED.</p>
                </div>
                <div class="foot-right">
                    <ul class="sns-list">
                        <li>
                            <a href="#" class="facebook" target="_blank"></a>
                        </li>
                        <li>
                            <a href="#" class="naver" target="_blank"></a>
                        </li>
                    </ul>
                    <select class="site-link" onchange="window.open(value,'_blank');">
                        <option selected>관련사이트 바로가기</option>
                        <option value="https://www.naver.com">네이버</option>
                    </select>
                </div>
            </div>
        </div>
    </footer>
</template>

<script lang="js">
export default  {
    name: 'Hooter',
    props: [],
    mounted() {

        $(".top-up-btn").on("click", function(){
            $('html, body').stop().animate( { scrollTop : '0' } );
        });
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
    .footer {
        background: #171d33;
    }
</style>
