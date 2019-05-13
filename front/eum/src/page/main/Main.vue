<template>

  <section class="main">
    <layout-basic>
      <div class="clide-container">
        <div class="container">
          <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
<!--             <ol class="carousel-indicators"> -->
<!--               <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li> -->
<!--               <li data-target="#carouselExampleIndicators" data-slide-to="1"></li> -->
<!--               <li data-target="#carouselExampleIndicators" data-slide-to="2"></li> -->
<!--             </ol> -->
            <div class="carousel-inner">
            	<div :class="`carousel-item ${(index == 0)?'active':''}`" v-for="(image, index) in banner.attachImage">
            		<img class="d-block w-100" style="height:364px;" :src="`${store.state.basePath}${image.resourcePath}/${image.virtualName}`">
            	</div>
            </div>
            <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
              <span class="carousel-control-prev-icon" aria-hidden="true"></span>
              <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
              <span class="carousel-control-next-icon" aria-hidden="true"></span>
              <span class="sr-only">Next</span>
            </a>
          </div>
        </div>
      </div>
      <div class="container">
        <div class="row">
          
          <div class="col-12 col-lg-6 h300 pd20">
            <div>공지사항</div>
            <table class="table">
            	<colgroup>
            		<col width="70%">
            		<col width="30%">
            	</colgroup>
              <thead>
                <tr>
                  <th scope="col" class="text-left">제목</th>
<!--                   <th scope="col" class="text-center">이름</th> -->
                </tr>
              </thead>
              <tbody>
              	<tr v-for="no in notice">
	                  <td class="text-left"><router-link :to="{path:`notice/${no.id}`}">{{no.bbs.title}}</router-link></td>
<!-- 	                  <td class="text-center"><template v-if="no.bbs.member">{{no.bbs.member.name}}</template></td> -->
              	</tr>
              </tbody>
            </table>
          </div>
          
          <div class="col-12 col-lg-6 h300 pd20">
            <div>프로그램</div>
            <table class="table">
              <thead>
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">First</th>
                  <th scope="col">Last</th>
                  <th scope="col">Handle</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <th scope="row">1</th>
                  <td>Mark</td>
                  <td>Otto</td>
                  <td>@mdo</td>
                </tr>
                <tr>
                  <th scope="row">2</th>
                  <td>Jacob</td>
                  <td>Thornton</td>
                  <td>@fat</td>
                </tr>
                <tr>
                  <th scope="row">3</th>
                  <td>Larry</td>
                  <td>the Bird</td>
                  <td>@twitter</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="row">
            <div class="col-12">활동게시판</div>
            <div class="row">
            	<div class="col-lg-3 col-md-6 mb-4" v-for="(content, index) in activity">
		            <div class="card h-100">
		              <img class="card-img-top" style="height:150px" v-if="content.attachImage[0]" :src="`${store.state.basePath}${content.attachImage[0].resourcePath}/s_${content.attachImage[0].virtualName}`" onerror="this.src='http://placehold.it/700x400'" alt="">
		              <div class="card-body" style="border-top: solid 1px #00000020;">
		                <p class="card-text"><router-link :to="{path:`activity/${content.id}`}">{{content.bbs.title}}</router-link></p>
		              </div>
		            </div>
		          </div>
            </div>
            
        </div>
      </div>
    </layout-basic>
  </section>

</template>

<script lang="js">
import LayoutBasic from '@/components/layouts/Basic'
  export default  {
    name: 'Main',
    props: [],
    mounted() {
    	this.bannerSearch();
		this.noticeSearch();
		this.activitySearch();
    },
    data() {
      return {
        notice : [],
        activity : [],
        banner : {}
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
			})
		},
		activitySearch : function(){
			this.$http.get(`${this.store.getters.restWebPath}/activity`,{params:{currentPage:1,limit:4}})
			.then(({data})=>{
				this.activity = data.list.content;
			})
		}
    },
    computed: {

    },
    components: {
      LayoutBasic
    }
}
</script>

<style scoped lang="scss">
  .main {
    
  }
  .clide-container{
    background: #cae0ea;
    padding: 20px 0;
  }

</style>
