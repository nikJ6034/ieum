<template>
    <section class="paging">
        <ul class="paging-list">
            <template v-if="startPage > blockSize">
                <li class="prev"><a @click="goPage(1)" >처음</a></li>
            </template>

            <template v-if="startPage > 1">
                <li class="prev"><a class="prev" @click="goPage(startPage-1)"></a></li>
            </template>
            <li v-for="n in ((endPage+1)-(startPage))">
                <template v-if="currentPage == ((n-1)+startPage)">
                    <a class="active">{{((n-1)+startPage)}}</a>
                </template>
                <template v-else>
                    <a @click="goPage(((n-1)+startPage))">{{((n-1)+startPage)}}</a>
                </template>
            </li>
            
            <template v-if="endPage < totalPages">
                <li class="next"><a class="next" @click="goPage(endPage+1)"></a></li>
                <li class="next"><a @click="goPage(totalPages)">끝</a></li>
            </template>
        </ul>
    </section>
</template>

<script lang="js">
export default  {
    name: 'pagination',
    // props: ['totalRows','perPage','blockRows', 'currentNum'],
    props:{
        totalRecords : {type : Number, default : 0}, //전체 게시물 수
        blockSize : {type : Number, default : 5}, // 한 화면에 보여줄 링크 수
        screenSize : {type : Number, default : 10}, // 한 화면에 보여줄 게시물 수
        currentPage : {type : Number, default : 1}, // 현재 페이지
        search : {type:Function}
    },
    mounted() {
        
    },
    data() {
        return {
            goPage : function(pageNum){
                if(this.search){
                    
                    this.search(pageNum);
                }
            }
        }
    },
    methods: {

    },
    computed: {
        startPage : function(){ // 한 구간의 첫 페이지 번호
            if(Math.floor((this.currentPage-1)/this.blockSize) == 0){
               return 1;
            }else{
                return (Math.floor((this.currentPage-1)/this.blockSize)*this.blockSize)+1;
            }
        },
        endPage : function(){ // 한 구간의 마지막 페이지 번호
            let endp = this.startPage + (this.blockSize -1);
            if(endp > this.totalPages){
                return this.totalPages;
            }else{
                return endp
            }
        },
        totalPages : function(){ // 전체 페이지 수
            return Math.ceil(this.totalRecords / this.screenSize);
        }
    }
}
</script>

<style scoped lang="scss">
    .pagination {

    }
</style>
<style scoped src="@/assets/css/paging.css"></style>