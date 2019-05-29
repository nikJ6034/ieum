<template>

  <section class="data-room-detail">
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

						<div class="board-view-header" style="border-bottom:1px solid #ddd;">
							<ul class="board-view-tit">
								<li class="title">{{dataRoom.title}}</li>
							</ul>

							<ul class="view-item">
								<li v-if="dataRoom.member">{{dataRoom.member.name}} <span>|</span></li><li>{{dataRoom.regDate|formatDate}}<!--<span>|</span>--></li><!--<li>0</li>-->
							</ul>
						</div>


						<div class="border-view-con ql-editor" v-html="dataRoom.content">


						</div>

						<!-- 첨부파일 -->
						<div class="board-file-box">
							<table class="board-file-list">
								<tbody>
								<tr v-if="dataRoom.attachFile" v-for="file in dataRoom.attachFile">
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
							<button v-if="this.store.state.menuRole.modifyRole=='Y'" type="button" class="btn" @click="goModifyPage">수정</button>
							<button v-if="this.store.state.menuRole.deleteRole=='Y'" type="button" class="btn li01" @click="deleteDataRoom">삭제</button>
							<router-link role="button" class="btn li02" to="/dataRoom">목록</router-link>
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
  export default  {
    name: 'data-room-detail',
    props: [],
    mounted() {
			this.search()
    },
    data() {
      return {
				dataRoom : {title:null, content:null}
      }
    },
    methods: {
			search : function(){
				this.$http.get(`${this.store.getters.restWebPath}/dataRoom/${this.$route.params.id}`)
				.then(({data})=>{
					this.dataRoom = data.data;
				})
			},
			deleteDataRoom : function(){
				if(window.confirm("정말로 삭제 하시겠습니까?")){
					this.$http.delete(`${this.store.getters.restWebPath}/dataRoom/${this.$route.params.id}`)
					.then(({data})=>{
						if(data.result == "success"){
							alert("삭제되었습니다.");
							this.router.push("/dataRoom");
						}else{
							alert("삭제되지 않았습니다.");
						}
					})
				}
			},
			goModifyPage : function(){
				this.router.push(`/dataRoom/modify/${this.$route.params.id}`);
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
  .data-room-detail {

  }
</style>
<style scoped src="@/assets/css/custom.css"></style>