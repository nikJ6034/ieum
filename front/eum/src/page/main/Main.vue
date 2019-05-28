<template>

  <section class="main">
    <layout-basic>
        <div id="container">
            <div id="content">
                <div class="visual-wrap">
                    <ul class="visual-slide">
                        <li v-for="image in banner.bannerImage">
                            <div class="con-area">
                                <div class="tit-box">
                                    <p class="tit" v-html="image.title">

                                    </p>
                                    <p v-html="image.content">>

                                    </p>

                                    <!--<a href="#">자세히 보기</a>-->
                                </div>
                            </div>
                            <div class="img-box" :style="`background-image: url('${store.state.basePath}${image.attachFile.resourcePath}/${image.attachFile.virtualName}')`"></div>
                        </li>
                    </ul>
                    <div class="con-area"><div id="sliderStatus"><span class="numerhere">1</span><span class="ltest"></span></div></div>

                    <div class="scroll-btn">
                        <span><img :src="require('@/assets/images/main/scroll_btn_img.png')" alt="SCROLL" /></span>
                    </div>
                </div>

                <div class="section-01">
                    <div class="con-area">
                        <div class="col-l">
                            <div class="con-h">
                                <h2>이음마을공지</h2>
                                <router-link to="/notice" class="more-btn">MORE</router-link>
                            </div>

                            <ul class="notice-list">
                                <li v-for="no in notice">
                                    <router-link :to="{path:`notice/${no.id}`}">
                                        <strong>{{no.title}}</strong>
                                        <p v-html="no.content"></p>
                                        <span class="date">{{no.regDate|formatDateKo2}}</span>
                                    </router-link>
                                </li>
                            </ul>

                        </div>

                        <div class="col-r">
                            <div class="con-h">
                                <h2>프로그램</h2>
                                <ul class="tab-tit">
                                    <li><a href="#tab01" class="active">꿈나래학교</a></li>
                                    <li><a href="#tab02">꿈과끼학교</a></li>
                                </ul>
                            </div>

                            <div class="tab-content">
                                <div id="tab01" class="tab-con active">
                                    <ul class="program-list">
                                        <li v-for="pro in narae">
                                            <div class="img-box"><img :src="`${store.state.basePath}${pro.imageFile.resourcePath}/${pro.imageFile.virtualName}`" :alt="pro.title" /></div>
                                            <div class="con-box">
                                                <strong>{{pro.title}}</strong>
                                                <ul>
                                                    <li><span>신청일</span><p>{{pro.strAppDate|formatDateKo2}}~{{pro.endAppDate|formatDateKo2}}</p></li>
                                                    <li><span>일시</span><p>{{pro.strDate|formatDateKo2}}~{{pro.endDate|formatDateKo2}}</p></li>
                                                    <li><span>장소</span><p>{{pro.address}} {{pro.addressDetail}}</p></li>
                                                </ul>
                                                <!--<p class="txt" v-html="pro.content"></p>-->
                                            </div>

                                        </li>
                                    </ul>

                                    <router-link to="/program/narae" class="more-btn">MORE</router-link>
                                </div>

                                <div id="tab02" class="tab-con">
                                    <ul class="program-list">
                                        <li v-for="pro in kki">
                                            <div class="img-box"><img :src="`${store.state.basePath}${pro.imageFile.resourcePath}/${pro.imageFile.virtualName}`" :alt="pro.title" /></div>
                                            <div class="con-box">
                                                <strong>{{pro.title}}</strong>
                                                <ul>
                                                    <li><span>신청일</span><p>{{pro.strAppDate|formatDateKo2}}~{{pro.endAppDate|formatDateKo2}}</p></li>
                                                    <li><span>일시</span><p>{{pro.strDate|formatDateKo2}}~{{pro.endDate|formatDateKo2}}</p></li>
                                                    <li><span>장소</span><p>{{pro.address}} {{pro.addressDetail}}</p></li>
                                                </ul>
                                                <!--<p class="txt" v-html="pro.content"></p>-->
                                            </div>

                                        </li>
                                    </ul>

                                    <router-link to="/program/kki" class="more-btn">MORE</router-link>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>

                <div class="section-02">
                    <div class="con-area">
                        <h2 class="tit">활동게시판</h2>
                        <p class="txt">이음마을학교의 다양한 활동을 소개합니다.</p>
                            <bx-slider :list="activity"></bx-slider>
                    </div>

                </div>


            </div>
        </div>
    </layout-basic>
  </section>

</template>

<script lang="js">
import '@/assets/css/main.css';
import LayoutBasic from '@/components/layouts/Basic'
import bxSlider from '@/components/slide/bxSlider'
import mainJs from '@/assets/js/main.js'
export default  {
    name: 'Main',
    props: [],
    mounted() {
    	this.bannerSearch();
        this.noticeSearch();
        this.activitySearch();
        this.programSearch();
    },
    data() {
      return {
        notice : [],
        activity : [],
        banner : [],
        narae : [],
        kki : []
      }
    },
    methods: {
    	noticeSearch : function(){
			this.$http.get(`${this.store.getters.restWebPath}/notice`,{params:{currentPage:1,limit:3}})
			.then(({data})=>{
				this.notice = data.list.content;
			})
		},
		bannerSearch : function(){
			this.$http.get(`${this.store.getters.restWebPath}/banner/1`,{})
			.then(({data})=>{
				this.banner = data.data;
                mainJs.main();
			})
		},
		activitySearch : function(){
			this.$http.get(`${this.store.getters.restWebPath}/activity`,{params:{currentPage:1,limit:10}})
			.then(({data})=>{
				this.activity = data.list.content;
			})
		},
        programSearch : function(){
            this.$http.get(`${this.store.getters.restWebPath}/program`,{params:{kind:1,currentPage:1,limit:2}})
                .then(({data})=>{
                    this.narae = data.list.content;
                })

            this.$http.get(`${this.store.getters.restWebPath}/program`,{params:{kind:2,currentPage:1,limit:2}})
                .then(({data})=>{
                    this.kki = data.list.content;
                })
        }
    },
    computed: {

    },
    components: {
      LayoutBasic,
        bxSlider
    }
}
</script>

<style scoped lang="scss">
  .main {
    
  }

</style>
