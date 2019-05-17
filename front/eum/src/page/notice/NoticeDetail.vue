<template>

  <section class="notice-detail">
    <sub-layout>
			<div>
				<div class="title">
					<h4><b>공지사항</b></h4>
				</div>
				<div class="row">
					<div class="col-12 subject">{{notice.title}}</div>
<!-- 					<div class="col-12 content" style="white-space: pre;">{{notice.content}}</div> -->
					<div class="col-12 content" v-html="notice.content"></div>
					<div class="col-12">
							<div class="d-inline-block mx5" v-if="notice.attachFile" v-for="file in notice.attachFile">
								<a class="btn btn-link" role="button" :href="store.getters.restWebPath+'/file/'+file.id">{{file.realName}}</a>
							</div>
					</div>
					<div class="col-12">
						<div class="btn-box float-right">
							<div>
								<button v-if="this.store.state.menuRole.modifyRole=='Y'" type="button" class="btn btn-warning" @click="goModifyPage">수정</button>
								<button v-if="this.store.state.menuRole.deleteRole=='Y'" type="button" class="btn btn-danger" @click="deleteNotice">삭제</button>
								<router-link role="button" class="btn btn-info" to="/notice">목록</router-link>
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
    name: 'notice-detail',
    props: [],
    mounted() {
			this.search()
    },
    data() {
      return {
				notice : {title:null, content:null}
      }
    },
    methods: {
			search : function(){
				this.$http.get(`${this.store.getters.restWebPath}/notice/${this.$route.params.id}`)
				.then(({data})=>{
					this.notice = data.data;
				})
			},
			deleteNotice : function(){
				if(window.confirm("정말로 삭제 하시겠습니까?")){
					this.$http.delete(`${this.store.getters.restWebPath}/notice/${this.$route.params.id}`)
					.then(({data})=>{
						if(data.result == "success"){
							alert("삭제되었습니다.");
							this.router.push("/notice");
						}else{
							alert("삭제되지 않았습니다.");
						}
					})
				}
			},
			goModifyPage : function(){
				this.router.push(`/notice/modify/${this.$route.params.id}`);
			},
			downloadFile : function(id){
				this.$http.get(`${this.store.getters.restWebPath}/download/${id}`,{responseType:'blob'})
				.then((response)=>{

				})
				window.console.log(id+"다운로드입니다.")
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
  .notice-detail {

  }
  
  .notice-detail .title {
	margin: 20px 0;
	font-size:30px;
	font-weight: 600;
  }
  
  .notice-detail .subject {
		border-top: solid 2px #66b1f1;
		padding: 15px;
		font-size:20px;
		font-weight: 500;
		min-height: 50px;
  }
  
  .notice-detail .content {
		border-top: solid 1px rgba(111, 111, 111, 0.5);
		padding: 10px 15px 0 15px;
		font-size:15px;
		min-height: 500px;
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
