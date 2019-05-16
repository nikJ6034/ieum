<template>
    <section class="program-modify">
        <sub-layout>
            <div>
                <div class="title">
                    <h4><b>프로그램 신청</b></h4>
                </div>
                <div class="row">
                    <div class="col-12 subject">{{(program.kind === 1)?'꿈나래 학교':'꿈과 끼 학교'}}</div>
                    <div class="col-12 subject">{{program.title}}</div>
                    <div class="col-12">일자 : {{program.strDate|formatDateKo2}}~{{program.endDate|formatDateKo2}}</div>
                    <div class="col-12">신청일 : {{program.strAppDate|formatDateKo2}}~{{program.endAppDate|formatDateKo2}}</div>
                    <div class="col-12">장소 : {{program.address}} {{program.addressDetail}}</div>
                    <div class="col-12">이미지 : <img v-if="program.imageFile" :src="`${store.state.basePath}${program.imageFile.resourcePath}/${program.imageFile.virtualName}`" style="height:150px;"/></div>
                    <div class="col-12 content" v-html="program.content"></div>
                    <div class="col-12">
                        이름 : <input type="text" readonly v-model="programApplication.name">
                    </div>
                    <div class="col-12">
                        전화번호 : <input type="text" v-model="programApplication.tel">
                    </div>
                    <div class="col-12">
                        특이사항 : <input type="text" v-model="programApplication.etc">
                    </div>
                    <div class="col-12">
                        <div class="d-inline-block mx5" v-if="program.attachFile" v-for="file in program.attachFile">
                            <a class="btn btn-link" role="button" :href="store.getters.restWebPath+'/file/'+file.id">{{file.realName}}</a>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="btn-box float-right">
                            <div>
                                <button v-if="isExpDate" type="button" class="btn btn-warning" @click="goApplication">신청</button>
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
import ProgramValidation from "./validation/ProgramValidation";
export default {
    name: "ProgramDetail",
    props : ["kind","id"],
    mounted() {
        if(this.kind == "narae"){
            this.program.kind = 1;
        }else if(this.kind == "kki"){
            this.program.kind = 2;
        }
        this.search();
    },
    data() {
        return {
            program : {
                id: null, kind : 1, address : "", addressDetail : "", addressNumber : null
                , strDate : "", endDate : "" ,strAppDate : null, endAppDate : null
                ,title:"", content:"",attachFile:[], imageFile: null, openType : "M"
            },
            programApplication : {memberId: this.store.state.ieumUserId, name:this.store.state.ieumUserName, tel:"", program: {id: null}, etc:""}
        }
    },
    methods: {
        search : function(){
            this.$http.get(`${this.store.getters.restWebPath}/program/${this.id}`)
                .then(({data})=>{
                    this.program = data.data;
                    this.programApplication.program.id = data.data.id;
                })
        },
        goApplication : function(){
            if(ProgramValidation.validation(this.programApplication)) return;

            if(confirm("신청하시겠습니까?")){
                this.$http.post(`${this.store.getters.restWebPath}/programApp` ,this.programApplication)
                    .then(({data})=>{
                        if(data.result == "success"){
                            alert(data.msg);
                            this.router.push(`/program/${this.kind}/${this.id}`);
                        }else{
                            alert(data.msg);
                        }
                    })
            }

            //alert(123);
            //this.router.push({path:`/program/${this.kind}/application/${this.id}`});
        }
    },
    computed: {
        isExpDate : function(){
            let strAppDate = new Date(this.program.strAppDate).getTime();
            let endAppDate = new Date(this.program.endAppDate).getTime();
            let curentTime = new Date().getTime();
            if(strAppDate <= curentTime && curentTime <=endAppDate){
                return true;
            }else{
                return false;
            }
        }
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
        min-height: 200px;
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