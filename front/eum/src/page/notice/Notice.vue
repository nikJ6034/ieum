<template>
    <section class="notice">
        <sub-layout>
            <!--<h4><b>공지사항</b></h4>
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
                        <td><router-link :to="{path:`notice/${content.id}`}">{{content.title}}</router-link></td>
                        <td>
                            <template>{{content.member.name}}</template>
                        </td>
                        <td>{{content.regDate | formatDate}}</td>
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
            </div>-->
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

                        <div class="board-list-header">
                            <div class="total-box">
                                전체 <strong>{{totalRows}}</strong>건
                            </div>
                            <form @submit="keywordSearch(1)" @submit.prevent>
                                <div class="search-box">
                                    <select class="select" name="">
                                        <option>전체</option>
                                    </select>
                                    <div class="input-group">
                                        <input type="text" class="intxt" v-model="searchForm.title" placeholder="검색어를 입력하세요." />
                                        <div class="in-btn">
                                            <button type="button" class="btn" @click="keywordSearch(1)">검색</button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <div class="btn-list">
                                <button type="button" class="btn li01" @click="goInsertPage">글쓰기</button>
                            </div>

                        </div>

                        <div class="borad-content">
                            <table class="board-con-list">
                                <colgroup>
                                    <col width="">
                                </colgroup>
                                <thead>
                                <tr>
                                    <th class="num">번호</th>
                                    <th>제목</th>
                                    <th class="author">작성자</th>
                                    <th class="date">등록일</th>
                                    <!--<th class="reading">조회수</th>-->
                                </tr>
                                </thead>
                                <tbody>

                                    <tr v-for="(content, index) in contents">
                                        <th class="num">{{number*size+index+1}}</th>
                                        <td class="bod-tit"><router-link :to="{path:`notice/${content.id}`}">{{content.title}}</router-link></td>
                                        <td class="author">{{content.member.name}}</td>
                                        <td class="date">{{content.regDate | formatDate}}</td>
                                        <!--<td class="reading">1</td>-->
                                    </tr>

                                </tbody>
                            </table>

                        </div>

                        <div class="paging">
                            <ul class="paging-list">
                                <li class="prev"><a href="#" class="prev"></a></li>
                                <li><a href="#" class="active">1</a></li>
                                <li><a href="#">2</a></li>
                                <li><a href="#">3</a></li>
                                <li class="next"><a href="#" class="next"></a></li>
                            </ul>
                        </div>

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
</style>
<style scoped src="@/assets/css/custom.css"></style>