<template>
	<section class="banner-modify">
		<sub-layout>
			<div>
				<div class="title">
					<h4><b>배너 관리</b></h4>
				</div>
				<form>
					<div class="row">
						<div class="col-12 subject">
							<label>제목</label>
							<input type="text" class="w-80 d-inline-block form-control" v-model="banner.title">
						</div>
						<div class="col-12 content">
							<textarea v-model="banner.description" class="form-control" rows="3"></textarea>
						</div>
						<div v-if="banner.bannerImage" v-for="bannerImg in banner.bannerImage">
							<div>
								<img :src="`${store.state.basePath}${bannerImg.attachFile.resourcePath}/${bannerImg.attachFile.virtualName}`" style="width:200px"> &nbsp;<button class="btn btn-link" type="button" @click="deleteFile(bannerImg.attachFile.id)">삭제</button>
								<input type="text" v-model="bannerImg.title"/>
								<textarea cols="50" rows="4" v-model="bannerImg.content"></textarea>
								<input style="width:30px" type="text" v-model="bannerImg.sortNumber"/>
							</div>
						</div>
						<div class="col-12 content">
							<div class="row">
								<div class="col-12">
									<button type="button" class="btn btn-info" @click="fileCountAdd">배너 추가</button>
								</div>
								<div class="col-12">
									<template v-for="n in fileCount">
										<div style="margin-top:5px;">
											<img :ref="`file_${n}`" style="width:200px;"/> &nbsp;
											<input type="file" :id="`file_${n}`" accept='image/jpeg,image/gif,image/png' v-on:change="uploadFile">
											<input type="text" v-model="bannerImage[n-1].title"/>
											<textarea cols="50" rows="4" v-model="bannerImage[n-1].content"></textarea>
											<input style="width:30px" type="text" v-model="bannerImage[n-1].soertNumber"/>
										</div>
									</template>
								</div>
							</div>
						</div>
						<div class="col-12">
							<div class="btn-box float-right">
								<div>
									<button type="button" class="btn btn-warning" @click="modify">수정</button>
									<router-link role="button" class="btn btn-info" to="/admin/banner">목록</router-link>
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
	name: 'banner-modify',
	props: [],
	mounted() {
		this.search()
	},
	data() {
		return {
			banner : {title: null, description : null ,attachImage:[], width:0, height : 0},
			files : {},
			bannerImage : [{title:"",content:"",sortNumber:0}],
			fileCount : 1
		}
	},
	methods: {
		cancel : function(){
			this.router.go(-1);
		},
		search : function(){
			this.$http.get(`${this.store.getters.restWebPath}/banner/${this.$route.params.id}`)
			.then(({data})=>{
				this.banner = data.data;
			})
		},
		modify : function(){
//			if(NoticeValidation.validation(this.notice)) return; //값 검증
			if(window.confirm("수정 하시겠습니까?")){
				let formData = new FormData();
				formData.append("banner",new Blob([JSON.stringify(this.banner)], {
					type: "application/json"
				}));

				formData.append("bannerImage",new Blob([JSON.stringify(this.bannerImage)], {
					type: "application/json"
				}))

				Object.keys(this.files).forEach((key)=>{
					formData.append("files",this.files[key]);
				});
				this.$http.post(
					`${this.store.getters.restWebPath}/banner/${this.$route.params.id}`
					,formData
					,{
						headers: {
							'Content-Type': 'multipart/mixed'
						}
				})
				.then(({data})=>{
					if(data.result === "success"){
						alert("수정 되었습니다.");
//						this.router.go();
						this.router.go(this.router.currentRoute)
// 						this.router.push(`/banner/modify/${this.$route.params.id}`);
					}else{
						alert("수정 되지 않았습니다.");
					}
				})
			}
		},
		uploadFile : function(event){
			let targetId = event.target.id;
			let _this = this;
			this.files[event.target.id]=event.target.files[0];

			if (event.target.files[0]) {
				let reader = new FileReader();
				reader.onload = function (e) {
					_this.$refs[targetId][0].src = e.target.result;
				};
				reader.readAsDataURL(event.target.files[0]);
			}
		},
		deleteFile : function(fileId){
			if(window.confirm("파일을 삭제 하시겠습니까?")){
				this.$http.delete(`${this.store.getters.restWebPath}/banner/image/${this.banner.id}/${fileId}`)
				.then((response)=>{
					if(response.data.result === "success"){
						this.banner.bannerImage = response.data.attachFile;
						alert(response.data.msg);
					}else{
						alert(response.data.msg);
						return false;
					}
				})
			}
		},
		parentsMethod: function(message) {
			this.banner.description = message;
		},
		fileCountAdd : function(){
			this.bannerImage.push({title:"",content:"", sortNumber:0});
			this.fileCount++;
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
	.banner-modify {

	}
  
	.banner-modify .title {
	margin: 20px 0;
	font-size:30px;
	font-weight: 600;
	}

	.banner-modify .subject {
		border-top: solid 2px #66b1f1;
	padding: 15px;
		font-size:20px;
		font-weight: 500;
		min-height: 50px;
  }

	.banner-modify .subject label{
		margin-right: 20px;
	}
  
	.banner-modify .content {
/*		border-top: solid 1px rgba(111, 111, 111, 0.5); */
		padding: 10px 15px 10px 15px;
		font-size:15px;
	}
  
	.btn-box button,a {
		margin: 0 10px 10px 0;
	}
</style>
