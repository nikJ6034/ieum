<template>
    <section class="programList">
        <sub-layout>
            <div id="content">
				<div id="navigator">
					<h3>{{kindName}}</h3>
					<ul>
						<li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
                        <li> > </li>
                        <li>관리자</li>
                        <li> > </li>
                        <li>프로그램</li>
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
                                    <select class="select" v-model="searchForm.kind" name="kind" @change="search(1)">
                                        <option v-for="pro in programKindList" :value="pro.id">{{pro.name}}</option>
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
                                <button v-if="this.store.state.menuRole.writeRole=='Y'" type="button" class="btn li01" @click="goInsertPage">작성</button>
							</div>

						</div>

						<div class="borad-content">

							<ul class="webzine-list">
								<li v-for="content in contents">
                                    <router-link :to="{path:`program/${content.id}`}">
										<div class="img"><img :src="`${store.state.basePath}${content.imageFile.resourcePath}/${content.imageFile.virtualName}`" :alt="content.title" /></div>
										<div class="con-box">
											<strong>{{content.title}}</strong>
											<ul>
												<li><span>신청일</span><p>{{content.strAppDate|formatDateKo2}}~{{content.endAppDate|formatDateKo2}}</p></li>
												<li><span>일시</span><p>{{content.strDate|formatDateKo2}}~{{content.endDate|formatDateKo2}}</p></li>
												<li><span>장소</span><p>{{content.address}} {{content.addressDetail}}</p></li>
											</ul>
										</div>
                                    </router-link>
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

<script>
import Pagination from '@/components/pagination/Pagination'
import SubLayout from '@/components/layouts/SubLayout';
import ProgramProp from "../../program/prop/ProgramProp.js"
export default {
    name: "ProgramList",
    mounted() {
        this.programKindList = ProgramProp.getProgramList();
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
            searchForm : {title:"",kind:1, currentPage : 1},
            programKindList : []
        }
    },
    methods: {
        search : function(pageNum){
            this.searchForm.currentPage = pageNum || 1;
            this.$http.get(`${this.store.getters.restWebPath}/program`,{params:this.searchForm})
                .then(({data})=>{
                    this.totalRows = data.list.totalElements;
                    this.contents = data.list.content;
                    this.number = data.list.number;
                    this.size = data.list.size;
                })
        },
        goInsertPage : function(){
            this.router.push({path:`/admin/program/insert`, query: { kind: this.searchForm.kind }});
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

<style scoped>
    .programList {

    }
</style>
<style scoped src="@/assets/css/custom.css"></style>