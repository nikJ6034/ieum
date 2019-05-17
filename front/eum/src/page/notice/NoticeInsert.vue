<template>
    <section class="notice-insert">
        <sub-layout>
            <div>
                <div class="title">
                    <h4><b>공지사항</b></h4>
                </div>
                <form>
                    <div class="row">
                        <div class="col-12 subject">
                            <label>제목</label>
                            <input type="text" class="w-80 d-inline-block form-control" v-model="notice.title">
                        </div>
                        <div class="col-12 content">
                            <quill :qContent.sync="notice.content"></quill>
                        </div>
                        <input type="file"  id="file1" v-on:change="uploadFile">
                        <div class="col-12">
                            <div class="btn-box float-right">
                                <div>
                                    <button v-if="store.state.menuRole.writeRole=='Y'" type="button" class="btn btn-warning" @click="save">저장</button>
                                    <button type="button" class="btn btn-danger" @click="cancel">취소</button>
                                    <router-link v-if="store.state.menuRole.readRole=='Y'" role="button" class="btn btn-info" to="/notice">목록</router-link>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
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
  
  .notice-insert .title {
	margin: 20px 0;
	font-size:30px;
	font-weight: 600;
  }
  
  .notice-insert .subject {
		border-top: solid 2px #66b1f1;
		padding: 15px;
		font-size:20px;
		font-weight: 500;
		min-height: 50px;
  }
  
  .notice-insert .subject label{
		margin-right: 20px;
  }
  
  .notice-insert .content {
		border-top: solid 1px rgba(111, 111, 111, 0.5);
		padding: 10px 15px 10px 15px;
		font-size:15px;
  }
  
  .notice-insert .content textarea{
		min-height: 500px;
  }
  
  .btn-box button,a {
		margin: 0 10px 10px 0;
  }
</style>
