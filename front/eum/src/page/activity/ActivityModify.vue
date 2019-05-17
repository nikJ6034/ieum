<template>

  <section class="activity-modify">
    <sub-layout>
			<div>
				<div class="title">
					<h4><b>활동사진 게시판</b></h4>
				</div>
				<form>
					<div class="row">
						<div class="col-12 subject">
							<label>제목</label>
							<input type="text" class="w-80 d-inline-block form-control" v-model="activity.title">
						</div>
						<div class="col-12 content">
							<quill v-if="activity.content != null" :qContent.sync="activity.content"></quill>
						</div>
						<div v-if="activity.attachImage" v-for="image in activity.attachImage">
							<div>
									<img :src="`${store.state.basePath}${image.resourcePath}/${image.virtualName}`" style="width:200px"> &nbsp;<button class="btn btn-link" type="button" @click="deleteFile(image.id)">삭제</button>
							</div>
						</div>
						<div class="col-12 content">
							<div class="row">
								<div class="col-12">
									<button type="button" class="btn btn-info" @click="fileCountAdd">첨부파일 추가</button>
								</div>
								<div class="col-12">
									<template v-for="n in fileCount">
										<div style="margin-top:5px;">
											<img :ref="`file_${n}`" style="width:200px;"/> &nbsp;
											<input type="file" :id="`file_${n}`" accept='image/jpeg,image/gif,image/png' v-on:change="uploadFile">
										</div>
									</template>
								</div>
							</div>
						</div>
						<div class="col-12">
							<div class="btn-box float-right">
								<div>
									<button v-if="this.store.state.menuRole.modifyRole=='Y'" type="button" class="btn btn-warning" @click="modify">수정</button>
									<button v-if="this.store.state.menuRole.deleteRole=='Y'" type="button" class="btn btn-danger" @click="cancel">취소</button>
									<router-link v-if="this.store.state.menuRole.readRole=='Y'" role="button" class="btn btn-info" to="/activity">목록</router-link>
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
import activityValidation from './validation/ActivityValidation';
import Quill from '@/components/editor/quill/Quill';

  export default  {
    name: 'activity-modify',
    props: [],
    mounted() {
			this.search()
    },
    data() {
      return {
				activity : {title: null, content : null,attachImage:[]},
				files : {},
				fileCount : 1
      }
    },
    methods: {
			cancel : function(){
				this.router.go(-1);
			},
			search : function(){
				this.$http.get(`${this.store.getters.restWebPath}/activity/${this.$route.params.id}`)
				.then(({data})=>{
					this.activity = data.data;
				})
			},
			modify : function(){
				if(activityValidation.validation(this.activity)) return; //값 검증.

				if(window.confirm("수정 하시겠습니까?")){
					let formData = new FormData();
					formData.append("activity",new Blob([JSON.stringify(this.activity)], {
							type: "application/json"
					}));
						
					Object.keys(this.files).forEach((key)=>{
						formData.append("files",this.files[key]);
					})
					this.$http.post(
					`${this.store.getters.restWebPath}/activity/${this.$route.params.id}`
					,formData
					,{
							headers: {
								'Content-Type': 'multipart/mixed'
							}
					})
					.then(({data})=>{
						if(data.result == "success"){
							alert("수정 되었습니다.");
							this.router.push(`/activity/${this.$route.params.id}`);
						}else{
							alert("수정 되지 않았습니다.");
						}
					})
				}
			},
			uploadFile : function(event){
				let targetId = event.target.id;
				let _this = this;
				this.files[event.target.id]=event.target.files[0]
				
				if (event.target.files[0]) {
					let reader = new FileReader();
			        reader.onload = function (e) {
			        	_this.$refs[targetId][0].src = e.target.result;
			        }
			        reader.readAsDataURL(event.target.files[0]);
			    }
			},
			deleteFile : function(fileId){
				if(window.confirm("파일을 삭제 하시겠습니까?")){
					this.$http.delete(`${this.store.getters.restWebPath}/activity/image/${this.activity.id}/${fileId}`)
					.then((response)=>{
						if(response.data.result == "success"){
							this.activity.attachImage = response.data.attachFile;
							alert(response.data.msg);
						}else{
							alert(response.data.msg);
							return false;
						}
					})
				}
			},
			fileCountAdd : function(){
				this.fileCount++;
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
  .activity-modify {

  }
  
  .activity-modify .title {
	margin: 20px 0;
	font-size:30px;
	font-weight: 600;
  }
  
  .activity-modify .subject {
		border-top: solid 2px #66b1f1;
		padding: 15px;
		font-size:20px;
		font-weight: 500;
		min-height: 50px;
  }
  
  .activity-modify .subject label{
		margin-right: 20px;
  }
  
  .activity-modify .content {
/* 		border-top: solid 1px rgba(111, 111, 111, 0.5); */
		padding: 10px 15px 10px 15px;
		font-size:15px;
  }
  
  .activity-modify .content textarea{
		min-height: 500px;
  }
  
  .btn-box button,a {
		margin: 0 10px 10px 0;
  }
</style>
