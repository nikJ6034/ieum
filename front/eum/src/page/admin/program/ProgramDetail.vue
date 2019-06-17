<template>
    <section class="program-modify">
        <sub-layout>
            <div id="content">
				<div id="navigator">
					<h3>{{kindName}}</h3>
					<ul>
						<li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
                        <li> > </li>
                        <li>프로그램</li>
                        <li> > </li>
                        <li>프로그램</li>
					</ul>
				</div>

				<div id="con">

					<div class="board-wrap">

						<div class="board-view-header" style="border-bottom:1px solid #ddd;">
							<ul class="board-view-tit">
								<li class="title">{{program.title}}</li>
							</ul>
						</div>


                        <div class="board-add-header" style="border-bottom:1px solid #ddd;">

							<ul>
								<li class="li01">일자</li>
								<li class="li05">
									<p class="con-txt">{{program.strDate|formatDateKo2}}~{{program.endDate|formatDateKo2}}</p>
								</li>
								<li class="li01">신청일</li>
								<li class="li05">
									<p class="con-txt">{{program.strAppDate|formatDateKo2}}~{{program.endAppDate|formatDateKo2}}</p>
								</li>
                                <li class="li01">장소</li>
								<li class="li05">
									<p class="con-txt">{{program.address}} {{program.addressDetail}}</p>
								</li>
							</ul>
                            <ul>
                                <li class="li05">
									<p class="con-txt"><img v-if="program.imageFile" :src="`${store.state.basePath}${program.imageFile.resourcePath}/${program.imageFile.virtualName}`" style="height:150px;"/></p>
								</li>
                            </ul>
						</div>

						<div class="border-view-con ql-editor" v-html="program.content"></div>

						<!-- 첨부파일 -->
						<div class="board-file-box">
							<table class="board-file-list">
								<tbody>
								<tr v-if="program.attachFile" v-for="file in program.attachFile">
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
                            <router-link role="button" class="btn" :to="`/admin/program/application/${program.id}`">신청</router-link>
                            <router-link role="button" class="btn li01" :to="`/admin/program/application/${program.id}/mng`">신청자 관리</router-link>
                            <button v-if="this.store.state.menuRole.modifyRole=='Y'" type="button" class="btn li02" @click="goModifyPage">수정</button>
                            <button v-if="this.store.state.menuRole.deleteRole=='Y'" type="button" class="btn li04" @click="deleteProgram">삭제</button>
                            <router-link role="button" class="btn li01" to="/admin/program">목록</router-link>
						</div>
						<!-- //버튼 -->
						<!-- //컨텐트 -->
					</div>
				</div>
			</div>
        </sub-layout>
    </section>
</template>

<script>
import SubLayout from '@/components/layouts/SubLayout'
import ProgramProp from "../../program/prop/ProgramProp.js"
export default {
    name: "ProgramDetail"
    ,mounted() {
        this.search()
    },
    data() {
        return {
            program : {
                kind : 1, address : "", addressDetail : "", addressNumber : null
                , strDate : "", endDate : "" ,strAppDate : "", endAppDate : ""
                ,title:"", content:"",attachFile:[], imageFile: null, openType : "M"
            },
            kindName : ""
        }
    },
    methods: {
        search : function(){
            this.$http.get(`${this.store.getters.restWebPath}/program/${this.$route.params.id}`)
                .then(({data})=>{
                    this.program = data.data;
                    this.kindName = ProgramProp.getKindName(this.program.kind);
                })
        },
        deleteProgram : function(){
            if(window.confirm("정말로 삭제 하시겠습니까?")){
                this.$http.delete(`${this.store.getters.restWebPath}/program/${this.$route.params.id}`)
                    .then(({data})=>{
                        if(data.result == "success"){
                            alert("삭제되었습니다.");
                            this.router.push("/admin/program");
                        }else{
                            alert("삭제되지 않았습니다.");
                        }
                    })
            }
        },
        goModifyPage : function(){
            this.router.push(`/admin/program/modify/${this.$route.params.id}`);
        },
        downloadFile : function(id){
            this.$http.get(`${this.store.getters.restWebPath}/download/${id}`,{responseType:'blob'})
                .then(()=>{

                })
        }
    },
    computed: {

    },
    components: {
        SubLayout
    }
}
</script>

<style scoped>
    .program-modify {

    }
</style>
<style scoped src="@/assets/css/custom.css"></style>