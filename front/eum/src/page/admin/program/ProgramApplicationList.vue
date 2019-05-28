<template>
    <section class="program-appList">
        <sub-layout>
            <div>
                <div class="title">
                    <h4><b>{{kindName}} 신청</b></h4>

                </div>
                <div class="row">
                    <div class="col-12 subject">{{program.title}}</div>
                    <div class="col-12">일자 : {{program.strDate|formatDateKo2}}~{{program.endDate|formatDateKo2}}</div>
                    <div class="col-12">신청일 : {{program.strAppDate|formatDateKo2}}~{{program.endAppDate|formatDateKo2}}</div>
                    <div class="col-12">장소 : {{program.address}} {{program.addressDetail}}</div>
                    <div class="col-12">
                        <table>
                            <colgroup>
                                <col width="5%">
                                <col width="10%">
                                <col width="15%">
                                <col width="">
                                <col width="10%">
                                <col width="5%">
                                <col width="5%">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th>번호</th>
                                    <th>이름</th>
                                    <th>전화번호</th>
                                    <th>신청 이유</th>
                                    <th>삭제 유무</th>
                                    <th>삭제</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="app in programApplicationList">
                                    <td>{{app.id}}</td>
                                    <td>{{app.name}}</td>
                                    <td>{{app.tel}}</td>
                                    <td>{{app.etc}}</td>
                                    <td>{{app.deleteYN}}</td>
                                    <td><button @click="deleteApp(app)">{{(app.deleteYN == 'N')?"삭제":"복구"}}</button></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>


                    <div class="col-12">
                        <div class="btn-box float-right">
                            <div>
                                <router-link role="button" class="btn btn-info" :to="`/admin/program/${this.id}`">이전</router-link>
                            </div>
                        </div>
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

    .program-appList .title {
        margin: 20px 0;
        font-size:30px;
        font-weight: 600;
    }

    .program-appList .subject {
        border-top: solid 2px #66b1f1;
        padding: 15px;
        font-size:20px;
        font-weight: 500;
        min-height: 50px;
    }

    .program-appList .content {
        border-top: solid 1px rgba(111, 111, 111, 0.5);
        padding: 10px 15px 0 15px;
        font-size:15px;
        min-height: 200px;
    }

    .btn-box button,a {
        margin: 0 10px 10px 0;
    }

</style>