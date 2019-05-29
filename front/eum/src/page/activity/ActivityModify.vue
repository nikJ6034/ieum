<template>

  <section class="activity-modify">
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
							<quill v-if="activity.content != null" :qContent.sync="activity.content"></quill>
						</div>

            <div class="board-file-box">
						<table class="board-file-list">
							<tbody>
							<tr v-if="activity.attachImage" v-for="image in activity.attachImage">
								<th class="title">첨부파일</th>
								<td>
									<ul>

										<li>
												<img :src="`${store.state.basePath}${image.resourcePath}/${image.virtualName}`" style="width:200px"> &nbsp;<button class="btn btn-link" type="button" @click="deleteFile(image.id)">삭제</button>
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
                  <router-link v-if="this.store.state.menuRole.readRole=='Y'" role="button" class="btn li02" to="/activity">목록</router-link>
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
/*     border-top: solid 1px rgba(111, 111, 111, 0.5); */
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
<style scoped src="@/assets/css/custom.css"></style>