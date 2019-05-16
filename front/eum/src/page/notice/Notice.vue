<template>
    <section class="notice">
        <sub-layout>
            <h4><b>공지사항</b></h4>
			<div class="searchDiv">
                <div class="row">
                    <label class="col-6 col-form-label"><b>총 게시물수</b><span class="totalRows">{{totalRows}}</span></label>
                    <div class="col-6">
                        <form @submit="keywordSearch(1)" @submit.prevent>
                            <div class="form-inline" style="float:right;">
                                <div class="input-group">
                                    <input type="text" id="searchKeyword" v-model="searchForm.title" class="form-control" placeholder="검색조건을 입력해주세요.">
                                    <span class="input-group-btn">
                                        <button type="button" class="btn btn-primary" @click="keywordSearch(1)">검색</button>
                                    </span>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <table class="table table-striped table-bordered">
                <colgroup>
                    <col width="10%">
                    <col width="60%">
                    <col width="15%">
                    <col width="15%">
                </colgroup>
                <thead class="thead-dark text-center">
                    <tr>
                        <th scope="col">번호</th>
                        <th scope="col">제목</th>
                        <th scope="col">작성자</th>
                        <th scope="col">작성일</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(content, index) in contents">
                        <th scope="row" class="text-center">{{number*size+index+1}}</th>
                        <td><router-link :to="{path:`notice/${content.id}`}">{{content.bbs.title}}</router-link></td>
                        <td>
                            <template>{{content.bbs.member.name}}</template>
                        </td>
                        <td>{{content.bbs.regDate | formatDate}}</td>
                    </tr>
                </tbody>
            </table>
            <div class="overflow-auto" >
                <div class="text-center">
                    <b-pagination align="center" size="md" :total-rows="totalRows" v-model="currentPage" :per-page="size" hide-ellipsis :limit="10" @change="search" />
                </div>
            </div>
            <div>
                <div class="btn-box float-right">
                    <div>
                        <button v-if="this.store.state.menuRole.writeRole=='Y'" type="button" class="btn btn-warning" @click="goInsertPage">작성</button>
                    </div>
                </div>
            </div>
        </sub-layout>
    </section>
</template>

<script lang="js">
import SubLayout from '@/components/layouts/SubLayout'
  export default  {
    name: 'notice',
    props: [],
    mounted() {
		
	},
	beforeMount(){
	    this.search()
	},
    data() {
      return {
        currentPage: 1,
        totalRows : 0,
        number : 0,
        size : 0,
        contents : {},
        searchForm : {title:""}
      }
    },
    methods: {
         search : async function(pageNum){
            this.searchForm.currentPage = pageNum || 1;
            let {data} = await this.$http.get(`${this.store.getters.restWebPath}/notice`,{params:this.searchForm});
             this.totalRows = data.list.totalElements;
             this.contents = data.list.content;
             this.number = data.list.number;
             this.size = data.list.size;
        },
        goInsertPage : function(){
            this.router.push(`/notice/insert`);
        },
        keywordSearch : function(pageNum){
            this.currentPage = pageNum;
            this.search(pageNum);
        }
    },
    computed: {

    },
    components:{
        SubLayout
    }
}
</script>

<style scoped lang="scss">
    .notice {

    }
  
    .searchDiv {
        padding: 20px;
        /*  border: solid 1px #ddd; */
        background: #71beff;
        border-radius: 10px;
        box-shadow: 4px 3px 11px 2px grey;
        margin: 0 0 20px 0;
    }
	
    .searchDiv .totalRows{
        margin-left: 10px
    }

	.table .thead-dark th{
        background-color: #67708b;
        border-color: #67708b;
	}
  
    .page-item.active .page-link{
        background-color: #a6b4c4;
        border-color : #a6b4c4;
    }
	
    .btn-box button,a {
        margin: 0 10px 10px 0;
    }
</style>
