<template>

  <section class="free-board-modify">
    <sub-layout>
      <div id="content">
        <div id="navigator">
          <h3>자유게시판</h3>
          <ul>
            <li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
            <li> > </li>
            <li>커뮤니티</li>
            <li> > </li>
            <li>자유게시판</li>
          </ul>
        </div>

        <div id="con">

          <div class="board-wrap">

            <div class="board-add-header" style="border-bottom:1px solid #ddd;">

            <ul>
                <li class="li01">제목</li>
                <li class="li04">
                    <input type="text" class="tit-int" v-model="freeBoard.title">
                </li>
            </ul>

            </div>
            <div class="border-view-con">
              <textarea v-model="freeBoard.content" rows="10" style="width:100%;"></textarea>
            </div>
            <!-- 버튼 -->
            <div class="board-btn-list">
              <button v-if="this.store.state.isAdmin || (store.state.ieumUserId == freeBoard.member.id)" type="button" class="btn" @click="modify">수정</button>
              <button v-if="this.store.state.isAdmin || (store.state.ieumUserId == freeBoard.member.id)" type="button" class="btn li01" @click="cancel">취소</button>
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
import SubLayout from '@/components/layouts/SubLayout'
import FreeBoardValidation from './validation/FreeBoardValidation';
  export default  {
    name: 'free-board-modify',
    props: [],
    mounted() {
      this.search()
    },
    data() {
      return {
        freeBoard : {title:null, content:null, member:{id:null}}
      }
    },
    methods: {
      cancel : function(){
      this.router.go(-1);
    },
    search : function(){
      this.$http.get(`${this.store.getters.restWebPath}/freeBoard/${this.$route.params.id}`)
      .then(({data})=>{
        this.freeBoard = data.data;
      })
    },
    modify : function(){
      if(FreeBoardValidation.validation(this.freeBoard)) return; //값 검증

      if(window.confirm("수정 하시겠습니까?")){
        this.$http.put(
          `${this.store.getters.restWebPath}/freeBoard/${this.$route.params.id}`
          ,this.freeBoard
        )
        .then(({data})=>{
          if(data.result == "success"){
            alert("수정 되었습니다.");
            this.router.push(`/freeBoard/${this.$route.params.id}`);
          }else{
            alert("수정 되지 않았습니다.");
          }
        })
      }
    },
    },
    computed: {
  
    },
    components: {
    SubLayout
  }
}
</script>

<style scoped lang="scss">
  .free-board-modify {

  }
</style>
<style scoped src="@/assets/css/custom.css"></style>