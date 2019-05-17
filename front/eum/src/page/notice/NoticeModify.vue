<template>

  <section class="notice-modify">
    <sub-layout>
			<div>
				<div class="title">
					<h4><b>공지사항</b></h4>
				</div>
				<form>
					<div class="row">
						<div class="col-12 subject">
							<label>제목</label>
							<input type="text" class="w-80 d-inline-block form-control" v-model="notice.title">
						</div>
						<div class="col-12 content">
							<quill v-if="notice.content != null" :qContent.sync="notice.content"></quill>
						</div>
						<div v-if="notice.attachFile" v-for="file in notice.attachFile">
							<div>
									{{file.realName}}<button class="btn btn-link" type="button" @click="deleteFile(file.id)">삭제</button>
							</div>
						</div>
						<div class="col-12">
							<template v-if="notice.attachFile.length == 0">
								<input type="file" id="attachFile" v-on:change="uploadFile">
							</template>
						</div>
						<div class="col-12">
							<div class="btn-box float-right">
								<div>
									<button v-if="this.store.state.menuRole.modifyRole=='Y'" type="button" class="btn btn-warning" @click="modify">수정</button>
									<button v-if="this.store.state.menuRole.deleteRole=='Y'" type="button" class="btn btn-danger" @click="cancel">취소</button>
									<router-link v-if="this.store.state.menuRole.readRole=='Y'" role="button" class="btn btn-info" to="/notice">목록</router-link>
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
import NoticeValidation from './validation/NoticeValidation'
import Quill from '@/components/editor/quill/Quill';

  export default  {
    name: 'notice-modify',
    props: [],
    mounted() {
			this.search()
    },
    data() {
      return {
				notice : {title: null, content : null,attachFile:[]},
				files : {}
      }
    },
    methods: {
			cancel : function(){
				this.router.go(-1);
			},
			search : function(){
				this.$http.get(`${this.store.getters.restWebPath}/notice/${this.$route.params.id}`)
				.then(({data})=>{
					this.notice = data.data;
				})
			},
			modify : function(){
				if(NoticeValidation.validation(this.notice)) return; //값 검증

				if(window.confirm("수정 하시겠습니까?")){
					let formData = new FormData();
					formData.append("notice",new Blob([JSON.stringify(this.notice)], {
							type: "application/json"
					}));
						
					Object.keys(this.files).forEach((key)=>{
						formData.append("files",this.files[key]);
					})
					this.$http.post(
					`${this.store.getters.restWebPath}/notice/${this.$route.params.id}`
					,formData
					,{
							headers: {
								'Content-Type': 'multipart/mixed'
							}
					})
					.then(({data})=>{
						if(data.result == "success"){
							alert("수정 되었습니다.");
							this.router.push(`/notice/${this.$route.params.id}`);
						}else{
							alert("수정 되지 않았습니다.");
						}
					})
				}
			},
			uploadFile : function(){
				this.files[event.target.id]=event.target.files[0];
			},
			deleteFile : function(fileId){
				if(window.confirm("파일을 삭제 하시겠습니까?")){
					this.$http.delete(`${this.store.getters.restWebPath}/notice/file/${this.notice.id}/${fileId}`)
					.then((response)=>{
						if(response.data.result == "success"){
							this.notice.attachFile = response.data.attachFile;
							alert(response.data.msg);
						}else{
							alert(response.data.msg);
							return false;
						}
					})
				}
			}
    },
    computed: {

    },
    components: {
			SubLayout,
			Quill
    }
}
</script>

<style scoped lang="scss">
  .notice-modify {

  }
  
  .notice-modify .title {
	margin: 20px 0;
	font-size:30px;
	font-weight: 600;
  }
  
  .notice-modify .subject {
		border-top: solid 2px #66b1f1;
		padding: 15px;
		font-size:20px;
		font-weight: 500;
		min-height: 50px;
  }
  
  .notice-modify .subject label{
		margin-right: 20px;
  }
  
  .notice-modify .content {
		border-top: solid 1px rgba(111, 111, 111, 0.5);
		padding: 10px 15px 10px 15px;
		font-size:15px;
  }
  
  .notice-modify .content textarea{
		min-height: 500px;
  }
  
  .btn-box button,a {
		margin: 0 10px 10px 0;
  }
</style>
