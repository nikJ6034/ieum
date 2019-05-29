<template>

  <section class="data-room-modify">
    <sub-layout>
			<div id="content">
			<div id="navigator">
				<h3>자료실</h3>
				<ul>
					<li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
					<li> > </li>
					<li>커뮤니티</li>
					<li> > </li>
					<li>자료실</li>
				</ul>
			</div>
			<div id="con">
				<div class="board-wrap">
					<div class="board-add-header" style="border-bottom:1px solid #ddd;">

						<ul>
							<li class="li01">제목</li>
							<li class="li04">
								<input type="text" class="tit-int" v-model="dataRoom.title">
							</li>
						</ul>

					</div>


					<div class="border-view-con">
						<quill v-if="dataRoom.content != null" :qContent.sync="dataRoom.content"></quill>
					</div>


					<div class="board-file-box" v-if="dataRoom.attachFile.length != 0">
						<table class="board-file-list">
							<tbody>
								<tr v-for="file in dataRoom.attachFile">
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
					<br/>
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
						<button v-if="this.store.state.menuRole.modifyRole=='Y'" type="button" class="btn" @click="modify">수정</button>
						<button v-if="this.store.state.menuRole.deleteRole=='Y'" type="button" class="btn li03" @click="cancel">취소</button>
						<router-link v-if="this.store.state.menuRole.readRole=='Y'" role="button" class="btn li02" to="/dataRoom">목록</router-link>
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
import DataRoomValidation from './validation/DataRoomValidation'
import Quill from '@/components/editor/quill/Quill';

  export default  {
    name: 'data-room-modify',
    props: [],
    mounted() {
			this.search()
    },
    data() {
      return {
				dataRoom : {title: null, content : null,attachFile:[]},
				files : {},
				fileCount : 1
      }
    },
    methods: {
			cancel : function(){
				this.router.go(-1);
			},
			search : function(){
				this.$http.get(`${this.store.getters.restWebPath}/dataRoom/${this.$route.params.id}`)
				.then(({data})=>{
					this.dataRoom = data.data;
				})
			},
			modify : function(){
				if(DataRoomValidation.validation(this.dataRoom)) return; //값 검증

				if(window.confirm("수정 하시겠습니까?")){
					let formData = new FormData();
					formData.append("dataRoom",new Blob([JSON.stringify(this.dataRoom)], {
							type: "application/json"
					}));
						
					Object.keys(this.files).forEach((key)=>{
						formData.append("files",this.files[key]);
					})
					this.$http.post(
					`${this.store.getters.restWebPath}/dataRoom/${this.$route.params.id}`
					,formData
					,{
							headers: {
								'Content-Type': 'multipart/mixed'
							}
					})
					.then(({data})=>{
						if(data.result == "success"){
							alert("수정 되었습니다.");
							this.router.push(`/dataRoom/${this.$route.params.id}`);
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
					this.$http.delete(`${this.store.getters.restWebPath}/dataRoom/file/${this.dataRoom.id}/${fileId}`)
					.then((response)=>{
						if(response.data.result == "success"){
							this.dataRoom.attachFile = response.data.attachFile;
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
  .data-room-modify {

  }
</style>
<style scoped src="@/assets/css/custom.css"></style>