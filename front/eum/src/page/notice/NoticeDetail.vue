<template>

  <section class="notice-detail">
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

						<div class="board-view-header" style="border-bottom:1px solid #ddd;">
							<ul class="board-view-tit">
								<li class="title">{{notice.title}}</li>
							</ul>

							<ul class="view-item">
								<li v-if="notice.member">{{notice.member.name}} <span>|</span></li><li>{{notice.regDate|formatDate}}<!--<span>|</span>--></li><!--<li>0</li>-->
							</ul>
						</div>


						<div class="border-view-con ql-editor" v-html="notice.content">


						</div>

						<!-- 첨부파일 -->
						<div class="board-file-box">
							<table class="board-file-list">
								<tbody>
								<tr v-if="notice.attachFile" v-for="file in notice.attachFile">
									<th class="title">첨부파일</th>
									<td>
										<ul>
											<li>
												<a :href="store.getters.restWebPath+'/file/'+file.id">{{file.realName}}</a>
											</li>
										</ul>
									</td>
								</tr>
								</tbody>
							</table>
						</div>

						<!-- //첨부파일 -->
						<!-- 버튼 -->
						<div class="board-btn-list">
							<input type="button" v-if="this.store.state.menuRole.modifyRole=='Y'" @click="goModifyPage"  class="btn" value="수정">
							<input type="button" v-if="this.store.state.menuRole.deleteRole=='Y'" @click="deleteNotice" class="btn li01" value="삭제">
							<router-link class="btn li02" to="/notice">목록</router-link>
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

import 'quill/dist/quill.core.css'
import SubLayout from '@/components/layouts/SubLayout'
  export default  {
    name: 'notice-detail',
    props: [],
    mounted() {
			this.search()
    },
    data() {
      return {
				notice : {title:null, content:null, member: null}
      }
    },
    methods: {
			search : function(){
				this.$http.get(`${this.store.getters.restWebPath}/notice/${this.$route.params.id}`)
				.then(({data})=>{
					this.notice = data.data;
				})
			},
			deleteNotice : function(){
				if(window.confirm("정말로 삭제 하시겠습니까?")){
					this.$http.delete(`${this.store.getters.restWebPath}/notice/${this.$route.params.id}`)
					.then(({data})=>{
						if(data.result == "success"){
							alert("삭제되었습니다.");
							this.router.push("/notice");
						}else{
							alert("삭제되지 않았습니다.");
						}
					})
				}
			},
			goModifyPage : function(){
				this.router.push(`/notice/modify/${this.$route.params.id}`);
			},
			downloadFile : function(id){
				this.$http.get(`${this.store.getters.restWebPath}/download/${id}`,{responseType:'blob'})
				.then((response)=>{

				})
				window.console.log(id+"다운로드입니다.")
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
  .notice-detail {

  }
</style>
<style scoped src="@/assets/css/custom.css"></style>
