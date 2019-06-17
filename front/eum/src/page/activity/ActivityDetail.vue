<template>
    <section class="activity-detail">
        <sub-layout>
            <div id="content">
				<div id="navigator">
					<h3>활동사진 게시판</h3>
					<ul>
						<li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
                        <li> > </li>
                        <li>소식</li>
                        <li> > </li>
                        <li>활동게시판</li>
					</ul>
				</div>

				<div id="con">

					<div class="board-wrap">

						<div class="board-view-header" style="border-bottom:1px solid #ddd;">
							<ul class="board-view-tit">
								<li class="title">{{activity.title}}</li>
							</ul>

							<ul class="view-item">
								<li v-if="activity.member">{{activity.member.name}} <span>|</span></li><li>{{activity.regDate|formatDate}}<!--<span>|</span>--></li><!--<li>0</li>-->
							</ul>
						</div>

						<div class="border-view-con ql-editor" v-html="activity.content">
						</div>

					<div>
                        <owl-slide v-if="activity.attachImage.length > 0" :images="activity.attachImage"
                            :options="{
                                loop:false,
                                margin:10,
                                autoplay:false,
                                autoplayTimeout:3000,
                                autoplayHoverPause:true,
                                dots : false
                            }">
                        </owl-slide>
                    </div>
						<!-- //첨부파일 -->
						<!-- 버튼 -->
						<div class="board-btn-list">
                            <button v-if="this.store.state.menuRole.modifyRole=='Y'" type="button" class="btn " @click="goModifyPage">수정</button>
                                <button v-if="this.store.state.menuRole.deleteRole=='Y'" type="button" class="btn li01" @click="deleteActivity">삭제</button>
							<router-link class="btn li02" to="/activity">목록</router-link>
						</div>
						<!-- //버튼 -->
						<!-- //컨텐트 -->
					</div>
				</div>
			</div>
        </sub-layout>
    </section>
</template>

<script lang="js">
import SubLayout from '@/components/layouts/SubLayout'
import OwlSlide from '@/components/slide/OwlSlide'
export default  {
    name: 'activity-detail',
    props: [],
    mounted() {
        this.search();
    },
    data() {
        return {
            activity : {title:null, content:null, attachImage : []}
        }
    },
    methods: {
        search : function(){
            this.$http.get(`${this.store.getters.restWebPath}/activity/${this.$route.params.id}`)
            .then(({data})=>{
                
                this.activity = data.data;
                $('.owl-carousel').owlCarousel({
                    loop:true,
                    margin:10,
                    nav:true,
                    autoplay:true,
                    autoplayTimeout:3000,
                    autoplayHoverPause:true
                })
            })
        },
        deleteActivity : function(){
            if(window.confirm("정말로 삭제 하시겠습니까?")){
                this.$http.delete(`${this.store.getters.restWebPath}/activity/${this.$route.params.id}`)
                .then(({data})=>{
                    if(data.result == "success"){
                        alert("삭제되었습니다.");
                        this.router.push("/activity");
                    }else{
                        alert("삭제되지 않았습니다.");
                    }
                })
            }
        },
        goModifyPage : function(){
            this.router.push(`/activity/modify/${this.$route.params.id}`);
        }
    },
    computed: {

    },
    components: {
        SubLayout,
        OwlSlide
    }
}
</script>

<style scoped lang="scss">
    .activity-detail {

    }
</style>
