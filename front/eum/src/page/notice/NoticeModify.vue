<template>

  <section class="notice-modify">
    <sub-layout>
		<div id="content">
			<div id="navigator">
				<h3>공지사항</h3>
				<ul>
					<li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
					<li> > </li>
					<li>소식</li>
					<li> > </li>
					<li>공지사항</li>
				</ul>
			</div>
			<div id="con">
				<div class="board-wrap">
					<div class="board-add-header" style="border-bottom:1px solid #ddd;">

						<ul>
							<li class="li01">제목</li>
							<li class="li04">
								<input type="text" class="tit-int" v-model="notice.title">
							</li>
						</ul>

					</div>


					<div class="border-view-con">
						<quill v-if="notice.content != null" :qContent.sync="notice.content"></quill>
					</div>


					<div class="board-file-box">
						<table class="board-file-list">
							<tbody>

							<tr v-if="notice.attachFile.length == 0">
								<th class="title">첨부파일</th>
								<td>
									<ul>

										<li>
												<input type="file" id="attachFile" v-on:change="uploadFile">
										</li>

									</ul>
								</td>
							</tr>
							<tr v-else v-for="file in notice.attachFile">
								<th class="title">첨부파일</th>
								<td>
									<ul>
										<li>
											<a style="display: inline-block" :href="store.getters.restWebPath+'/file/'+file.id">{{file.realName}}</a> <button class="btn li04" type="button" @click="deleteFile(file.id)">삭제</button>
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
						<input type="button" v-if="this.store.state.menuRole.modifyRole=='Y'" @click="modify" class="btn" value="저장">
						<input type="button" @click="cancel" class="btn li03" value="취소">
						<router-link v-if="this.store.state.menuRole.readRole=='Y'" role="button" class="btn li02" to="/notice">목록</router-link>
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
</style>
<style scoped src="@/assets/css/custom.css"></style>