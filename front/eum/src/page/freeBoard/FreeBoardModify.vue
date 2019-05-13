<template>

  <section class="free-board-modify">
    <sub-layout>
			<div>
				<div class="title">
					<h4><b>자유게시판</b></h4>
				</div>
				<form>
					<div class="row">
						<div class="col-12 subject">
							<label>제목</label>
							<input type="text" class="w-80 d-inline-block form-control" v-model="freeBoard.bbs.title">
						</div>
						<div class="col-12 content">
							<textarea v-model="freeBoard.bbs.content" class="form-control"></textarea>
						</div>
						<div class="col-12">
							<div class="btn-box float-right">
								<div>
									<button v-if="this.store.state.isAdmin || (store.state.ieumUserId == freeBoard.bbs.member.id)" type="button" class="btn btn-warning" @click="modify">수정</button>
									<button v-if="this.store.state.isAdmin || (store.state.ieumUserId == freeBoard.bbs.member.id)" type="button" class="btn btn-danger" @click="cancel">취소</button>
<!-- 									<router-link v-if="this.store.state.menuRole.readRole=='Y'" role="button" class="btn btn-info" to="/notice">목록</router-link> -->
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
    </sub-layout>
  </section>

</template>

<script lang="js">
import SubLayout from '@/components/layouts/SubLayout'
  export default  {
    name: 'free-board-modify',
    props: [],
    mounted() {
    	this.search()
    },
    data() {
      return {
    	  freeBoard : {bbs:{title:null, content:null, member:{id:null}}}
      }
    },
    methods: {
    	cancel : function(){
			this.router.go(-1);
		},
		search : function(){
			this.$http.get(`${this.store.getters.restWebPath}/freeBoard/${this.$route.params.id}`)
			.then(({data})=>{
				this.freeBoard = data.data;
			})
		},
		modify : function(){
// 			if(NoticeValidation.validation(this.notice)) return; //값 검증

			if(window.confirm("수정 하시겠습니까?")){
				this.$http.put(
					`${this.store.getters.restWebPath}/freeBoard/${this.$route.params.id}`
					,this.freeBoard
				)
				.then(({data})=>{
					if(data.result == "success"){
						alert("수정 되었습니다.");
						this.router.push(`/freeBoard/${this.$route.params.id}`);
					}else{
						alert("수정 되지 않았습니다.");
					}
				})
			}
		},
    },
    computed: {
	
    },
    components: {
		SubLayout
	}
}
</script>

<style scoped lang="scss">
  .free-board-modify {

  }
  
  .free-board-modify .title {
	margin: 20px 0;
	font-size:30px;
	font-weight: 600;
  }
  
  .free-board-modify .subject {
		border-top: solid 2px #66b1f1;
		padding: 15px;
		font-size:20px;
		font-weight: 500;
		min-height: 50px;
  }
  
  .free-board-modify .subject label{
		margin-right: 20px;
  }
  
  .free-board-modify .content {
		border-top: solid 1px rgba(111, 111, 111, 0.5);
		padding: 10px 15px 10px 15px;
		font-size:15px;
  }
  
  .free-board-modify .content textarea{
		min-height: 500px;
  }
  
  .btn-box button,a {
		margin: 0 10px 10px 0;
  }
</style>
