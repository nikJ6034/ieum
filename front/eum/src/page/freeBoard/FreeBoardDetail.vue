<template>

  <section class="free-board-detail">
    <sub-layout>
			<div>
				<div class="title">
					<h4><b>자유게시판</b></h4>
				</div>
				<div class="row">
					<div class="col-12 subject">{{freeBoard.title}}</div>
					<div class="col-12 content" style="white-space: pre;">{{freeBoard.content}}</div>
<!-- 					<div class="col-12 content" v-html="notice.content"></div> -->
					<div class="col-12">
						<div class="btn-box float-right">
							<div>
								<button v-if="this.store.state.isAdmin || (store.state.ieumUserId == freeBoard.member.id)" type="button" class="btn btn-warning" @click="goModifyPage">수정</button>
								<button v-if="this.store.state.isAdmin || (store.state.ieumUserId == freeBoard.member.id)" type="button" class="btn btn-danger" @click="deleteFreeBoard">삭제</button>
								<router-link role="button" class="btn btn-info" to="/freeBoard">목록</router-link>
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
  
  .free-board-detail .title {
	margin: 20px 0;
	font-size:30px;
	font-weight: 600;
  }
  
  .free-board-detail .subject {
		border-top: solid 2px #66b1f1;
		padding: 15px;
		font-size:20px;
		font-weight: 500;
		min-height: 50px;
  }
  
  .free-board-detail .content {
		border-top: solid 1px rgba(111, 111, 111, 0.5);
		padding: 10px 15px 0 15px;
		font-size:15px;
		min-height: 500px;
  }
  
  .btn-box button,a {
		margin: 0 10px 10px 0;
  }
</style>
