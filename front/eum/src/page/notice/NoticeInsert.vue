<template>
    <section class="notice-insert">
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

                        <div class="board-add-header" style="border-bottom:1px solid #ddd;">

                            <ul>
                                <li class="li01">제목</li>
                                <li class="li04">
                                    <input type="text" name="" class="tit-int" v-model="notice.title">
                                </li>
                            </ul>

                        </div>


                        <div class="border-view-con">
                            <quill :qContent.sync="notice.content"></quill>
                        </div>

                        <div class="board-file-box">
                            <table class="board-file-list">
                                <tbody>
                                    <tr>
                                        <th class="title">첨부파일</th>
                                        <td>
                                            <ul>

                                                <li>
                                                    <input type="file"  id="file1" size="50" v-on:change="uploadFile">
                                                </li>

                                            </ul>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <!--<table class="flie-list-all">
                            <tbody><tr>
                                <th>첨부가능한 파일</th>
                                <td><span>gif</span><span>jpg</span><span>png</span><span>hwp</span><span>doc</span><span>docx</span><span>xls</span><span>pdf</span><span>zip</span><span>csv</span><span>xlsx</span></td>
                            </tr>
                            </tbody>
                        </table>-->
                        <!-- 버튼 -->
                        <div class="board-btn-list">
                            <input type="button"  class="btn" value="저장" v-if="store.state.menuRole.writeRole=='Y'" @click="save">
                            <router-link v-if="store.state.menuRole.readRole=='Y'" class="btn li02" to="/notice">목록</router-link>
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
import SubLayout from '@/components/layouts/SubLayout';
import NoticeValidation from './validation/NoticeValidation';
import Quill from '@/components/editor/quill/Quill';

export default  {
    name: 'notice-insert',
    props: [],
    mounted() {

    },
    data() {
        return {
            notice : {title:"", content:"",attachFile:[]},
            files : {}
        }
    },
    methods: {
        cancel : function(){
            this.router.go(-1);
        },
        save : function(){
            if(NoticeValidation.validation(this.notice)) return; //값 검증
            if(window.confirm("작성 하시겠습니까?")){
                let formData = new FormData();
                formData.append("notice",new Blob([JSON.stringify(this.notice)], {
                    type: "application/json"
                }));
                Object.keys(this.files).forEach((key)=>{
                    formData.append("files",this.files[key]);
                })

                this.$http.post(`${this.store.getters.restWebPath}/notice`
                ,formData
                ,{
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'multipart/form-data'
                    }
                })
                .then(({data})=>{
                    if(data.result == "success"){
                        alert("작성 되었습니다.");
                        this.router.push(`/notice/${data.id}`);
                    }else{
                        alert("작성 되지 않았습니다.");
                    }
                })
            }
        },
        uploadFile : function(event){
            this.files[event.target.id]=event.target.files[0]
        }
    },
    computed: {

    },
    components: {
        SubLayout,
        Quill
    }
}
</script>

<style scoped lang="scss">
  .notice-insert {

  }
</style>
<style scoped src="@/assets/css/custom.css"></style>
