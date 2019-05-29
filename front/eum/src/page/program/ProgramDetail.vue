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

                        <div v-if="exists" class="board-add-header">
                            <ul>
								<li class="li01">신청자</li>
								<li class="li05">
									<p class="con-txt">{{programApplication.name}}</p>
								</li>
								<li class="li01">연락처</li>
								<li class="li05">
									<p class="con-txt">{{programApplication.tel}}</p>
								</li>
							</ul>
                        </div>

						<!-- 버튼 -->
						<div class="board-btn-list">
                            <button v-if="!exists" type="button" class="btn" @click="goApplication">신청</button>
                            <router-link role="button" class="btn li02" :to="`/program/${this.kind}`">목록</router-link>
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
import ProgramProp from "./prop/ProgramProp"
export default {
    name: "ProgramDetail",
    props : ["kind","id"],
    mounted() {
        this.program.kind = ProgramProp.getKindCode(this.kind);
        this.kindName = ProgramProp.getKindName(this.kind);
        this.search();
        this.existsApp();
    },
    data() {
        return {
            program : {
                kind : 1, address : "", addressDetail : "", addressNumber : null
                , strDate : "", endDate : "" ,strAppDate : "", endAppDate : ""
                ,title:"", content:"",attachFile:[], imageFile: null, openType : "M"
            },
            exists : false
            ,programApplication : {memberId: null, name:null, tel:"", program: {id: null}, etc:""}
            ,kindName : ""
        }
    },
    methods: {
        search : function(){
            this.$http.get(`${this.store.getters.restWebPath}/program/${this.id}`)
                .then(({data})=>{
                    this.program = data.data;
                })
        },
        existsApp : function(){
            this.$http.get(`${this.store.getters.restWebPath}/programApp/exists/${this.id}`)
                .then(({data})=>{
                    this.exists = data.exists||false;
                    this.programApplication = data.programApplication;
                })
        },
        goApplication : function(){
            this.router.push({path:`/program/${this.kind}/application/${this.id}`});
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