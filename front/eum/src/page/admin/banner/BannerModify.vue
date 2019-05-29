<template>
	<section class="banner-modify">
		<sub-layout>
				<div id="content">
					<div id="navigator">
						<h3>배너 관리</h3>
						<ul>
							<li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
							<li> > </li>
							<li>관리자</li>
							<li> > </li>
							<li>배너 관리</li>
						</ul>
					</div>

					<div id="con">
						<div class="board-wrap">
							<div class="board-add-header" style="border-bottom:1px solid #ddd;">

								<ul>
									<li class="li01">제목</li>
									<li class="li04">
										<input type="text" class="tit-int" v-model="banner.title">
									</li>
								</ul>

							</div>

							<div class="border-view-con">
								<textarea v-model="banner.description" style="width:100%" rows="3"></textarea>
							</div>

							<div class="board-file-box">
								<table class="board-file-list">
									<tbody>

									<tr v-if="banner.bannerImage" v-for="bannerImg in banner.bannerImage" >
										<th class="title">
											<img :src="`${store.state.basePath}${bannerImg.attachFile.resourcePath}/${bannerImg.attachFile.virtualName}`" style="width:200px"> &nbsp;
										</th>
										<td>
											<textarea style="width:100%" rows="4" v-model="bannerImg.title"></textarea>
										</td>
										<td>
											<textarea style="width:100%" rows="4" v-model="bannerImg.content"></textarea>
										</td>
										<td>
											<input style="width:30px" type="text" v-model="bannerImg.sortNumber"/>
										</td>
										<td>
											<button class="btn li03" type="button" @click="deleteFile(bannerImg.attachFile.id)">삭제</button>
										</td>
									</tr>
									</tbody>
								</table>
							</div>
							<br/>
							<div>
									<button type="button" class="btn btn-info" @click="fileCountAdd">배너 추가</button>
							</div>
							<div class="board-file-box">
								<table class="board-file-list">
									<tbody>

									<tr v-for="n in fileCount" >
										<th class="title">
											<img :ref="`file_${n}`" style="width:200px;"/>
											<input type="file" :id="`file_${n}`" accept='image/jpeg,image/gif,image/png' v-on:change="uploadFile">
										</th>
										<td>
											<textarea style="width:100%" rows="4" v-model="bannerImage[n-1].title"></textarea>
										</td>
										<td>
											<textarea style="width:100%" rows="4" v-model="bannerImage[n-1].content"></textarea>
										</td>
										<td>
											<input style="width:30px" type="text" v-model="bannerImage[n-1].sortNumber"/>
										</td>
										<td>
											<button class="btn li03" type="button" @click="deleteFile(bannerImg.attachFile.id)">삭제</button>
										</td>
									</tr>
									</tbody>
								</table>
							</div>
							<!-- 버튼 -->
							<div class="board-btn-list">
								<button type="button" class="btn" @click="modify">수정</button>
								<router-link role="button" class="btn li02" to="/admin/banner">목록</router-link>
							</div>
							<!-- //버튼 -->
						</div>
					</div>
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
</style>
<style scoped src="@/assets/css/custom.css"></style>