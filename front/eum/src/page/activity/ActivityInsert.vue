<template>
	<section class="activity-insert">
		<sub-layout>
			<div id="content">
				<div id="navigator">
					<h3>활동사진 게시판</h3>
					<ul>
						<li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
                        <li> > </li>
                        <li>소식</li>
                        <li> > </li>
                        <li>활동게시판</li>
					</ul>
				</div>

				<div id="con">

					<div class="board-wrap">

						<div class="board-add-header" style="border-bottom:1px solid #ddd;">

							<ul>
								<li class="li01">제목</li>
								<li class="li04">
									<input type="text" name="" class="tit-int" v-model="activity.title">
								</li>
							</ul>

						</div>


						<div class="border-view-con">
							<quill :qContent.sync="activity.content"></quill>
						</div>
						<div>
							<button type="button" class="btn btn-info" @click="fileCountAdd">첨부파일 추가</button>
						</div>
						<div class="board-file-box">
							<table class="board-file-list">
								<tbody>

								<tr v-for="n in fileCount">
									<th class="title">첨부파일</th>
									<td>
										<ul>

											<li>
												<img :ref="`file_${n}`" style="width:200px;"/> &nbsp;
												<input type="file" :id="`file_${n}`" accept='image/jpeg,image/gif,image/png' v-on:change="uploadFile">
											</li>

										</ul>
									</td>
								</tr>
								</tbody>
							</table>
						</div>

						<!--<table class="flie-list-all">
							<tbody><tr>
								<th>첨부가능한 파일</th>
								<td><span>gif</span><span>jpg</span><span>png</span><span>hwp</span><span>doc</span><span>docx</span><span>xls</span><span>pdf</span><span>zip</span><span>csv</span><span>xlsx</span></td>
							</tr>
							</tbody>
						</table>-->


						<!-- 버튼 -->
						<div class="board-btn-list">
							<input type="button" v-if="store.state.menuRole.writeRole=='Y'" @click="save"  class="btn" value="저장">
							<input type="button" @click="cancel"  class="btn li03" value="취소">
							<router-link v-if="store.state.menuRole.readRole=='Y'" role="button" class="btn li02" to="/activity">목록</router-link>
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
import SubLayout from '@/components/layouts/SubLayout';
import activityValidation from './validation/ActivityValidation';
import Quill from '@/components/editor/quill/Quill';

export default  {
	name: 'activity-insert',
	props: [],
	mounted() {

	},
	data() {
		return {
			activity : {title:"", content:"",attachFile:[]},
			files : {},
			fileCount : 1
		}
	},
	methods: {
		cancel : function(){
			this.router.go(-1);
		},
		save : function(){
			if(activityValidation.validation(this.activity)) return; //값 검증.

			if(window.confirm("작성 하시겠습니까?")){
				let formData = new FormData();
				formData.append("activity",new Blob([JSON.stringify(this.activity)], {type: "application/json"}));
				Object.keys(this.files).forEach((key)=>{
					formData.append("files",this.files[key]);
				});

				this.$http.post(`${this.store.getters.restWebPath}/activity`
					,formData
					,{
					headers: {
						'Accept': 'application/json',
						'Content-Type': 'multipart/form-data'
					}
				})
				.then(({data})=>{
					if(data.result == "success"){
						alert("작성 되었습니다.");
						this.router.push(`/activity/${data.id}`);
					}else{
						alert("작성 되지 않았습니다.");
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
				}
				reader.readAsDataURL(event.target.files[0]);
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
	.activity-insert {

	}
</style>
<style scoped src="@/assets/css/custom.css"></style>