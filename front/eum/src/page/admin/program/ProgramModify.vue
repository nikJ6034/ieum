<template>
    <section class="program-detail">
        <sub-layout>
            <div>
                <div class="title">
                    <h4><b>프로그램</b></h4>
                </div>
                <div class="row">
                    <div class="col-12">
                        <select name="kind" v-model="program.kind">
                            <option v-for="pro in programKindList" :value="pro.id">{{pro.name}}</option>
                        </select>
                    </div>
                    <div class="col-12">
                        <label>제목</label>
                        <input type="text" class="w-80 d-inline-block form-control" v-model="program.title">
                    </div>
                    <div class="col-12">
                        <label>일자</label>
                        <datepicker :language="ko" v-model="program.strDate" :format="customFormatter"></datepicker>~
                        <datepicker :language="ko" v-model="program.endDate" :format="customFormatter"></datepicker>
                    </div>
                    <div class="col-12">
                        <label>신청기간</label>
                        <datepicker :language="ko" v-model="program.strAppDate" :format="customFormatter"></datepicker>~
                        <datepicker :language="ko" v-model="program.endAppDate" :format="customFormatter"></datepicker>
                    </div>
                    <div class="col-12 content">
                        <label>장소</label>
                        <input type="text" v-model="program.addressNumber" readonly> <button type="button" @click="postPopup">우편번호 검색</button>
                        <label>주소</label> <input v-model="program.address" type="text" readonly>
                        <label>상세 주소</label><input type="text" v-model="program.addressDetail">
                    </div>
                    <div class="col-12 content">
                        이미지파일 <input type="file"  id="image" v-on:change="uploadImage"> <img v-if="program.imageFile" :src="`${store.state.basePath}${program.imageFile.resourcePath}/${program.imageFile.virtualName}`" style="height:150px;"/>
                    </div>
                    <div class="col-12 content">
                        <quill v-if="program.content" :qContent.sync="program.content"></quill>
                    </div>
                    <div v-if="program.attachFile" v-for="file in program.attachFile">
                        <div>
                            {{file.realName}}<button class="btn btn-link" type="button" @click="deleteFile(file.id)">삭제</button>
                        </div>
                    </div>
                    <div class="col-12 content">
                        <div class="row">
                            <div class="col-12">
                                <button type="button" class="btn btn-info" @click="fileCountAdd">첨부파일 추가</button>
                            </div>
                            <div class="col-12">
                                <template v-for="n in fileCount">
                                    <div style="margin-top:5px;">
                                        <img :ref="`file_${n}`" style="width:200px;"/> &nbsp;
                                        <input type="file" :id="`file_${n}`" accept='image/jpeg,image/gif,image/png' v-on:change="uploadFile">
                                    </div>
                                </template>
                            </div>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="btn-box float-right">
                            <div>
                                <button v-if="this.store.state.menuRole.modifyRole=='Y'" type="button" class="btn btn-warning" @click="modify">수정</button>
                                <button v-if="this.store.state.menuRole.deleteRole=='Y'" type="button" class="btn btn-danger" @click="cancel">취소</button>
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
import SubLayout from '@/components/layouts/SubLayout';
import Quill from '@/components/editor/quill/Quill';
import Datepicker from 'vuejs-datepicker';
import {en, ko} from 'vuejs-datepicker/dist/locale';
import moment from 'moment';
import ProgramProp from "../../program/prop/ProgramProp.js"

export default {
    name: "ProgramModify"
    ,mounted() {
        this.programKindList = ProgramProp.getProgramList();
        this.search()
    },
    data() {
        return {
            program : {
                kind : 1, address : "", addressDetail : "", addressNumber : null
                , strDate : "", endDate : "" ,strAppDate : "", endAppDate : ""
                ,title:"", content:"",attachFile:[], imageFile: null, openType : "M"
            },
            files : {},
            fileCount : 1,
            image : null,
            en: en,
            ko: ko,
            programKindList : []
        }
    },
    methods: {
        search : function(){
            this.$http.get(`${this.store.getters.restWebPath}/program/${this.$route.params.id}`)
                .then(({data})=>{
                    this.program = data.data;
                })
        },
        modify : function(){
            if(window.confirm("수정 하시겠습니까?")){
                let formData = new FormData();
                formData.append("program",new Blob([JSON.stringify(this.program)], {
                    type: "application/json"
                }));

                Object.keys(this.files).forEach((key)=>{
                    formData.append("files",this.files[key]);
                });

                formData.append("image", this.image);

                this.$http.post(
                    `${this.store.getters.restWebPath}/program/${this.$route.params.id}`
                    ,formData
                    ,{
                        headers: {
                            'Content-Type': 'multipart/mixed'
                        }
                    })
                    .then(({data})=>{
                        if(data.result == "success"){
                            alert("수정 되었습니다.");
                            this.router.push(`/admin/program/${this.$route.params.id}`);
                        }else{
                            alert("수정 되지 않았습니다.");
                        }
                    })
            }
        },
        uploadFile : function(event){
            let targetId = event.target.id;
            let _this = this;
            this.files[event.target.id]=event.target.files[0];

            if (event.target.files[0]) {
                let reader = new FileReader();
                reader.onload = function (e) {
                    _this.$refs[targetId][0].src = e.target.result;
                }
                reader.readAsDataURL(event.target.files[0]);
            }
        },
        fileCountAdd : function(){
            this.fileCount++;
        },
        uploadImage : function(event){
            this.image = event.target.files[0];
        },
        customFormatter(date) {
            return moment(date).format('YYYY-MM-DD');
        },
        cancel : function(){
            this.router.go(-1);
        },
        deleteFile : function(fileId){
            if(window.confirm("파일을 삭제 하시겠습니까?")){
                this.$http.delete(`${this.store.getters.restWebPath}/program/file/${this.program.id}/${fileId}`)
                    .then((response)=>{
                        if(response.data.result == "success"){
                            this.program.attachFile = response.data.attachFile;
                            alert(response.data.msg);
                        }else{
                            alert(response.data.msg);
                            return false;
                        }
                    })
            }
        },
        postPopup(){
            let _this = this;
            new daum.Postcode({
                oncomplete: function(data) {
                    // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                    // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                    // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                    var addr = ''; // 주소 변수
                    var extraAddr = ''; // 참고항목 변수

                    //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                    if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                        addr = data.roadAddress;
                    } else { // 사용자가 지번 주소를 선택했을 경우(J)
                        addr = data.jibunAddress;
                    }

                    // 우편번호와 주소 정보를 해당 필드에 넣는다.
                    _this.program.addressNumber = data.zonecode;
                    _this.program.address = addr;
                }
            }).open();
        }
    },
    computed: {

    },
    components: {
        SubLayout,
        Quill,
        Datepicker
    }
}
</script>

<style scoped>

</style>