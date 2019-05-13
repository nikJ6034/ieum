<template>
    <section class="activity-detail">
        <sub-layout>
            <div>
                <div class="title">
                    <h4><b>활동사진 게시판</b></h4>
                </div>
                <div class="row">
                    <div class="col-12 subject">{{activity.bbs.title}}</div>
<!--                <div class="col-12 content" style="white-space: pre;">{{activity.bbs.content}}</div> -->
                    <div class="col-12 content" v-html="activity.bbs.content"></div>
                    <div class="col-12" style="margin-bottom:10px;">
                        <owl-slide v-if="activity.attachImage.length > 0" :images="activity.attachImage"
                            :options="{
                                loop:true,
                                margin:10,
                                autoplay:true,
                                autoplayTimeout:3000,
                                autoplayHoverPause:true,
                                dots : false
                            }">
                        </owl-slide>
                    </div>
                    <div class="col-12">
                        <div class="btn-box float-right">
                            <div>
                                <button v-if="this.store.state.menuRole.modifyRole=='Y'" type="button" class="btn btn-warning" @click="goModifyPage">수정</button>
                                <button v-if="this.store.state.menuRole.deleteRole=='Y'" type="button" class="btn btn-danger" @click="deleteActivity">삭제</button>
                                <router-link role="button" class="btn btn-info" to="/activity">목록</router-link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </sub-layout>
    </section>
</template>

<script lang="js">
import SubLayout from '@/components/layouts/SubLayout'
// import 'owl.carousel/dist/assets/owl.carousel.css';
// import 'owl.carousel/dist/assets/owl.theme.default.css';
// import carousel from 'vue-owl-carousel'
// import 'owl.carousel';
import OwlSlide from '@/components/slide/OwlSlide'
export default  {
    name: 'activity-detail',
    props: [],
    mounted() {
        this.search();
    },
    data() {
        return {
            activity : {bbs:{title:null, content:null}, attachImage : []}
        }
    },
    methods: {
        search : function(){
            this.$http.get(`${this.store.getters.restWebPath}/activity/${this.$route.params.id}`)
            .then(({data})=>{
                this.activity = data.data;
                let basePath = this.store.state.basePath;
//              data.data.attachImage.forEach(function(data){
//              let img = $("<img>").attr("src",basePath+data.resourcePath+"/"+data.virtualName);
//              $('.owl-carousel').append(img);
//              })
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
        },
        downloadFile : function(id){
            this.$http.get(`${this.store.getters.restWebPath}/download/${id}`,{responseType:'blob'})
            .then((response)=>{

            })
            window.console.log(id+"다운로드입니다.")
        },
        test : function(){
            console.log(123);
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
  
    .activity-detail .title {
        margin: 20px 0;
        font-size:30px;
        font-weight: 600;
    }
  
    .activity-detail .subject {
        border-top: solid 2px #66b1f1;
        padding: 15px;
        font-size:20px;
        font-weight: 500;
        min-height: 50px;
    }
  
    .activity-detail .content {
        border-top: solid 1px rgba(111, 111, 111, 0.5);
        padding: 10px 15px 0 15px;
        font-size:15px;
        min-height: 300px;
    }
  
    .btn-box button,a {
        margin: 0 10px 10px 0;
    }

    .btn-file {
        color: #ffffff;
        background-color: brown;
        border-color: brown;
    }
</style>
