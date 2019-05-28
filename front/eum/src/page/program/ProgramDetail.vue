<template>
    <section class="program-modify">
        <sub-layout>
            <div>
                <div class="title">
                    <h4><b>{{kindName}}</b></h4>
                </div>
                <div class="row">
                    <div class="col-12 subject">{{program.title}}</div>
                    <div class="col-12">일자 : {{program.strDate|formatDateKo2}}~{{program.endDate|formatDateKo2}}</div>
                    <div class="col-12">신청일 : {{program.strAppDate|formatDateKo2}}~{{program.endAppDate|formatDateKo2}}</div>
                    <div class="col-12">장소 : {{program.address}} {{program.addressDetail}}</div>
                    <div class="col-12">이미지 : <img v-if="program.imageFile" :src="`${store.state.basePath}${program.imageFile.resourcePath}/${program.imageFile.virtualName}`" style="height:150px;"/></div>
                    <div class="col-12 content" v-html="program.content"></div>
                    <div class="col-12">
                        <div class="d-inline-block mx5" v-if="program.attachFile" v-for="file in program.attachFile">
                            <a class="btn btn-link" role="button" :href="store.getters.restWebPath+'/file/'+file.id">{{file.realName}}</a>
                        </div>
                    </div>
                    <div v-if="exists" class="col-12">
                        신청자 : {{programApplication.name}}
                        연락처 : {{programApplication.tel}}
                    </div>
                    <div class="col-12">
                        <div class="btn-box float-right">
                            <div>
                                <button v-if="!exists" type="button" class="btn btn-warning" @click="goApplication">신청</button>
                                <router-link role="button" class="btn btn-info" :to="`/program/${this.kind}`">목록</router-link>
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

    .program-modify .title {
        margin: 20px 0;
        font-size:30px;
        font-weight: 600;
    }

    .program-modify .subject {
        border-top: solid 2px #66b1f1;
        padding: 15px;
        font-size:20px;
        font-weight: 500;
        min-height: 50px;
    }

    .program-modify .content {
        border-top: solid 1px rgba(111, 111, 111, 0.5);
        padding: 10px 15px 0 15px;
        font-size:15px;
        min-height: 500px;
    }

    .btn-box button,a {
        margin: 0 10px 10px 0;
    }

    .btn-file {
        color: #ffffff;
        background-color: brown;
        border-color: brown;
    }
</style>