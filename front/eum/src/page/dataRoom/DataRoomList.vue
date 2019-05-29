<template>

  <section class="data-room-list">
    <sub-layout>
         <div id="content">
                <div id="navigator">
                    <h3>자료실</h3>
                    <ul>
                        <li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
                        <li> > </li>
                        <li>커뮤니티</li>
                        <li> > </li>
                        <li>자료실</li>
                    </ul>
                </div>

                <div id="con">

                    <div class="board-wrap">

                        <div class="board-list-header">
                            <div class="total-box">
                                전체 <strong>{{totalRows}}</strong>건
                            </div>
                            <form @submit="search(1)" @submit.prevent>
                                <div class="search-box">
                                    <select class="select" name="">
                                        <option>전체</option>
                                    </select>
                                    <div class="input-group">
                                        <input type="text" class="intxt" v-model="searchForm.title" placeholder="검색어를 입력하세요." />
                                        <div class="in-btn">
                                            <button type="button" class="btn" @click="search(1)">검색</button>
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
                                        <td class="bod-tit"><router-link :to="{path:`dataRoom/${content.id}`}">{{content.title}}</router-link></td>
                                        <td class="author">{{content.member.name}}</td>
                                        <td class="date">{{content.regDate | formatDate}}</td>
                                        <!--<td class="reading">1</td>-->
                                    </tr>

                                </tbody>
                            </table>

                        </div>
                        <pagination :totalRecords="totalRows" :currentPage="searchForm.currentPage" :screenSize="size" :search="search"></pagination>
                    </div>
                </div>
            </div>
    
    </sub-layout>
  </section>

</template>

<script lang="js">
import Pagination from '@/components/pagination/Pagination'
import SubLayout from '@/components/layouts/SubLayout'
  export default  {
    name: 'data-room-list',
    props: [],
    mounted() {
    
    },
    beforeMount(){
      this.search()
    },
    data() {
      return {
      totalRows : 0,
      number : 0,
      size : 0,
      contents : {},
      searchForm : {title:"",currentPage: 1}
      }
    },
    methods: {
      search : function(pageNum){
        this.searchForm.currentPage = pageNum || 1;
        this.$http.get(`${this.store.getters.restWebPath}/dataRoom`,{params:this.searchForm})
        .then(({data})=>{
          this.totalRows = data.list.totalElements;
          this.contents = data.list.content;
          this.number = data.list.number;
          this.size = data.list.size;
        })
      },
      goInsertPage : function(){
        this.router.push(`/dataRoom/insert`);
      }
    },
    computed: {

    },
    components:{
      SubLayout,
      Pagination
    }
}
</script>

<style scoped lang="scss">
  .data-room-list {
  
  }
</style>
<style scoped src="@/assets/css/custom.css"></style>