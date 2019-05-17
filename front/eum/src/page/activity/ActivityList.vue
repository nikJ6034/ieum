<template>
    <section class="activity-list">
        <sub-layout>
            <h4><b>활동사진 게시판</b></h4>
            <div class="searchDiv">
                <div class="row">
                    <label class="col-6 col-form-label"><b>총 게시물수</b><span class="totalRows">{{totalRows}}</span></label>
                    <div class="col-6">
                        <form @submit="keywordSearch(1)" @submit.prevent>
                            <div class="form-inline" style="float:right;">
                                <div class="input-group">
                                    <input type="text" id="searchKeyword" v-model="searchForm.title" class="form-control" placeholder="검색조건을 입력해주세요."/>
                                    <span class="input-group-btn">
                                        <button type="button" class="btn btn-primary" @click="keywordSearch(1)">검색</button>
                                    </span>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-4 col-md-6 mb-4" v-for="(content, index) in contents">
                    <div class="card h-100">
                        <img class="card-img-top" style="height:150px" v-if="content.attachImage[0]" :src="`${store.state.basePath}${content.attachImage[0].resourcePath}/${content.attachImage[0].virtualName}`" onerror="this.src='http://placehold.it/700x400'" alt="">
                        <div class="card-body" style="border-top: solid 1px #00000020;">
                            <p class="card-text"><router-link :to="{path:`activity/${content.id}`}">{{content.title}}</router-link></p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="overflow-auto" >
                <div class="text-center">
                    <b-pagination align="center" size="md" :total-rows="totalRows" v-model="currentPage" :per-page="size" hide-ellipsis :limit="10" @change="search" />
                </div>
            </div>
            <div>
                <div class="btn-box float-right">
                    <div>
                        <button v-if="this.store.state.menuRole.writeRole == 'Y'" type="button" class="btn btn-warning" @click="goInsertPage">작성</button>
                    </div>
                </div>
            </div>
        </sub-layout>
    </section>
</template>

<script lang="js">
import SubLayout from '@/components/layouts/SubLayout'
export default  {
    name: 'activity-list',
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
        search : function(pageNum){
            this.searchForm.currentPage = pageNum || 1;
            this.$http.get(`${this.store.getters.restWebPath}/activity`,{params:this.searchForm})
            .then(({data})=>{
                this.totalRows = data.list.totalElements;
                this.contents = data.list.content;
                this.number = data.list.number;
                this.size = data.list.size;
            })
        },
        goInsertPage : function(){
            this.router.push(`/activity/insert`);
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
    .activity-list {

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
