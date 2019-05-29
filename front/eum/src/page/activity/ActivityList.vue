<template>
    <section class="activity-list">
        <sub-layout>
            <div id="content">
				<div id="navigator">
					<h3>활동사진 게시판</h3>
					<ul>
						<li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
                        <li> > </li>
                        <li>소식</li>
                        <li> > </li>
                        <li>활동게시판</li>
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
								<button v-if="this.store.state.menuRole.writeRole == 'Y'" @click="goInsertPage" type="button" class="btn li01">글쓰기</button>
							</div>

						</div>

						<div class="borad-content">

							<ul class="gallery-list">
								<li v-for="content in contents">
									<routerLink :to="{path:`activity/${content.id}`}">
										<div class="img"><img v-if="content.attachImage[0]" :src="`${store.state.basePath}${content.attachImage[0].resourcePath}/${content.attachImage[0].virtualName}`" onerror="this.src='http://placehold.it/700x400'" alt="" /></div>
										<div class="con-box">
											<strong>{{content.title}}</strong>
											<span>{{content.regDate | formatDate}}</span>
										</div>
									</routerLink>
								</li>
							</ul>

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
    name: 'activity-list',
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
            contents : [],
            searchForm : {title:"",currentPage: 1, limit:9}
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
    .activity-list {

    }
</style>
<style scoped src="@/assets/css/custom.css"></style>