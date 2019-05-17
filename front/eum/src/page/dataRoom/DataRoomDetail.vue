<template>

  <section class="data-room-detail">
    <sub-layout>
			<div>
				<div class="title">
					<h4><b>자료실</b></h4>
				</div>
				<div class="row">
					<div class="col-12 subject">{{dataRoom.title}}</div>
<!-- 					<div class="col-12 content" style="white-space: pre;">{{dataRoom.content}}</div> -->
					<div class="col-12 content" v-html="dataRoom.content"></div>
					<div class="col-12">
							<div class="d-inline-block mx5" v-if="dataRoom.attachFile" v-for="file in dataRoom.attachFile">
								<a class="btn btn-link" role="button" :href="store.getters.restWebPath+'/file/'+file.id">{{file.realName}}</a>
							</div>
					</div>
					<div class="col-12">
						<div class="btn-box float-right">
							<div>
								<button v-if="this.store.state.menuRole.modifyRole=='Y'" type="button" class="btn btn-warning" @click="goModifyPage">수정</button>
								<button v-if="this.store.state.menuRole.deleteRole=='Y'" type="button" class="btn btn-danger" @click="deleteDataRoom">삭제</button>
								<router-link role="button" class="btn btn-info" to="/dataRoom">목록</router-link>
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
    name: 'data-room-detail',
    props: [],
    mounted() {
			this.search()
    },
    data() {
      return {
				dataRoom : {title:null, content:null}
      }
    },
    methods: {
			search : function(){
				this.$http.get(`${this.store.getters.restWebPath}/dataRoom/${this.$route.params.id}`)
				.then(({data})=>{
					this.dataRoom = data.data;
				})
			},
			deleteDataRoom : function(){
				if(window.confirm("정말로 삭제 하시겠습니까?")){
					this.$http.delete(`${this.store.getters.restWebPath}/dataRoom/${this.$route.params.id}`)
					.then(({data})=>{
						if(data.result == "success"){
							alert("삭제되었습니다.");
							this.router.push("/dataRoom");
						}else{
							alert("삭제되지 않았습니다.");
						}
					})
				}
			},
			goModifyPage : function(){
				this.router.push(`/dataRoom/modify/${this.$route.params.id}`);
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
  .data-room-detail {

  }
  
  .data-room-detail .title {
	margin: 20px 0;
	font-size:30px;
	font-weight: 600;
  }
  
  .data-room-detail .subject {
		border-top: solid 2px #66b1f1;
		padding: 15px;
		font-size:20px;
		font-weight: 500;
		min-height: 50px;
  }
  
  .data-room-detail .content {
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
