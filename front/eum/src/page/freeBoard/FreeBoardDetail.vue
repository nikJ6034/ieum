<template>

  <section class="free-board-detail">
    <sub-layout>
      <div id="content">
				<div id="navigator">
					<h3>자유게시판</h3>
					<ul>
						<li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
						<li> > </li>
						<li>커뮤니티</li>
						<li> > </li>
						<li>자유게시판</li>
					</ul>
				</div>

				<div id="con">

					<div class="board-wrap">

						<div class="board-view-header" style="border-bottom:1px solid #ddd;">
							<ul class="board-view-tit">
								<li class="title">{{freeBoard.title}}</li>
							</ul>

							<ul class="view-item">
								<li v-if="freeBoard.member">{{freeBoard.member.name}} <span>|</span></li><li>{{freeBoard.regDate|formatDate}}<!--<span>|</span>--></li><!--<li>0</li>-->
							</ul>
						</div>


						<div class="border-view-con" style="white-space: pre;">
              <p>{{freeBoard.content}}</p>
						</div>

						<!-- 버튼 -->
						<div class="board-btn-list">
              <button v-if="this.store.state.isAdmin || (store.state.ieumUserId == freeBoard.member.id)" type="button" class="btn" @click="goModifyPage">수정</button>
              <button v-if="this.store.state.isAdmin || (store.state.ieumUserId == freeBoard.member.id)" type="button" class="btn li01" @click="deleteFreeBoard">삭제</button>
              <router-link role="button" class="btn li02" to="/freeBoard">목록</router-link>
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
  export default  {
    name: 'free-board-detail',
    props: [],
    mounted() {
      this.search()
    },
    data() {
      return {
        freeBoard : {title:null, content:null, member:{id:null}}
      }
    },
    methods: {
      search : function(){
      this.$http.get(`${this.store.getters.restWebPath}/freeBoard/${this.$route.params.id}`)
      .then(({data})=>{
        this.freeBoard = data.data;
      })
    },
    deleteFreeBoard : function(){
      if(window.confirm("정말로 삭제 하시겠습니까?")){
        this.$http.delete(`${this.store.getters.restWebPath}/freeBoard/${this.$route.params.id}`)
        .then(({data})=>{
          if(data.result == "success"){
            alert("삭제되었습니다.");
            this.router.push("/freeBoard");
          }else{
            alert("삭제되지 않았습니다.");
          }
        })
      }
    },
    goModifyPage : function(){
      this.router.push(`/freeBoard/modify/${this.$route.params.id}`);
    }
    },
    computed: {

    },
    components: {
    SubLayout
  }
}
</script>

<style scoped lang="scss">
  .free-board-detail {

  }
</style>
<style scoped src="@/assets/css/custom.css"></style>