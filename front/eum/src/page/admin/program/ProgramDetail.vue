<template>
    <section class="program-modify">
        <sub-layout>
            <div>
                <div class="title">
                    <h4><b>프로그램</b></h4>
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
                        <div class="d-inline-block mx5" v-if="program.attachFile" v-for="file in program.attachFile">
                            <a class="btn btn-link" role="button" :href="store.getters.restWebPath+'/file/'+file.id">{{file.realName}}</a>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="btn-box float-right">
                            <div>
                                <button v-if="this.store.state.menuRole.modifyRole=='Y'" type="button" class="btn btn-warning" @click="goModifyPage">수정</button>
                                <button v-if="this.store.state.menuRole.deleteRole=='Y'" type="button" class="btn btn-danger" @click="deleteProgram">삭제</button>
                                <router-link role="button" class="btn btn-info" to="/admin/program">목록</router-link>
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
            }
        }
    },
    methods: {
        search : function(){
            this.$http.get(`${this.store.getters.restWebPath}/program/${this.$route.params.id}`)
                .then(({data})=>{
                    this.program = data.data;
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
                .then((response)=>{

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