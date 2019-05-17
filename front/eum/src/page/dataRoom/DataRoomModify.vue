<template>

  <section class="data-room-modify">
    <sub-layout>
			<div>
				<div class="title">
					<h4><b>자료실</b></h4>
				</div>
				<form>
					<div class="row">
						<div class="col-12 subject">
							<label>제목</label>
							<input type="text" class="w-80 d-inline-block form-control" v-model="dataRoom.title">
						</div>
						<div class="col-12 content">
							<quill v-if="dataRoom.content != null" :qContent.sync="dataRoom.content"></quill>
						</div>
						<div v-if="dataRoom.attachFile" v-for="file in dataRoom.attachFile">
							<div>
									{{file.realName}}<button class="btn btn-link" type="button" @click="deleteFile(file.id)">삭제</button>
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
											<input type="file" :id="`file_${n}`" v-on:change="uploadFile">
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
									<router-link v-if="this.store.state.menuRole.readRole=='Y'" role="button" class="btn btn-info" to="/dataRoom">목록</router-link>
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
  
  .data-room-modify .title {
	margin: 20px 0;
	font-size:30px;
	font-weight: 600;
  }
  
  .data-room-modify .subject {
		border-top: solid 2px #66b1f1;
		padding: 15px;
		font-size:20px;
		font-weight: 500;
		min-height: 50px;
  }
  
  .data-room-modify .subject label{
		margin-right: 20px;
  }
  
  .data-room-modify .content {
		border-top: solid 1px rgba(111, 111, 111, 0.5);
		padding: 10px 15px 10px 15px;
		font-size:15px;
  }
  
  .data-room-modify .content textarea{
		min-height: 500px;
  }
  
  .btn-box button,a {
		margin: 0 10px 10px 0;
  }
</style>
