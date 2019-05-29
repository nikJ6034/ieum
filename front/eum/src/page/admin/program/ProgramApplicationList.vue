<template>
    <section class="program-appList">
        <sub-layout>
            <div id="content">
                <div id="navigator">
                    <h3>{{kindName}} 신청자 목록</h3>
                    <ul>
                        <li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
                        <li> > </li>
                        <li>관리자</li>
                        <li> > </li>
                        <li>신청자 목록</li>
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

                        <div class="borad-content">
                            <table class="board-con-list">
                                <colgroup>
                                    <col width="">
                                </colgroup>
                                <thead>
                                <tr>
                                    <!--th>번호</th-->
                                    <th>이름</th>
                                    <th>전화번호</th>
                                    <th>신청 이유</th>
                                    <th>삭제 유무</th>
                                    <th>삭제</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="app in programApplicationList">
                                        <!--td>{{app.id}}</td-->
                                        <td>{{app.name}}</td>
                                        <td>{{app.tel}}</td>
                                        <td>{{app.etc}}</td>
                                        <td>{{app.deleteYN}}</td>
                                        <td><button @click="deleteApp(app)" class="btn">{{(app.deleteYN == 'N')?"삭제":"복구"}}</button></td>
                                    </tr>
                                </tbody>
                            </table>

                        </div>

                        <!-- 버튼 -->
                        <div class="board-btn-list">
                            <router-link role="button" class="btn" :to="`/admin/program/${this.id}`">이전</router-link>
                        </div>
                        <!-- //버튼 -->

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
        name: "ProgramApplicationList",
        props : ["id"],
        mounted() {
            this.search();
        },
        data() {
            return {
                program : {
                    id: null, kind : 1, address : "", addressDetail : "", addressNumber : null
                    , strDate : "", endDate : "" ,strAppDate : null, endAppDate : null
                    ,title:"", content:"",attachFile:[], imageFile: null, openType : "M"
                },
                programApplicationList : [],
                kindName: ""
            }
        },
        methods: {
            search : function(){
                this.$http.get(`${this.store.getters.restWebPath}/program/${this.id}`)
                    .then(({data})=>{
                        this.program = data.data;
                        this.kindName = ProgramProp.getKindName(this.program.kind);
                    })
                this.$http.get(`${this.store.getters.restWebPath}/programApp/program/${this.id}`)
                    .then(({data})=>{
                        this.programApplicationList = data.list;
                    })
            },
            deleteApp: function(app){
                this.$http.delete(`${this.store.getters.restWebPath}/programApp/${app.id}`)
                    .then(({data})=>{
                        alert(data.msg);
                        app.deleteYN = data.deleteYN;
                    })
            }
        },
        components: {
            SubLayout
        }
    }
</script>

<style scoped>
    .program-appList {

    }

</style>
<style scoped src="@/assets/css/custom.css"></style>