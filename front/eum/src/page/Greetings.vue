<template>

  <section class="greetings">
    <sub-layout>
      <div id="content">
        <div id="navigator">
            <h3>인사말</h3>
            <ul>
                <li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
                <li> > </li>
                <li>소개</li>
                <li> > </li>
                <li>인사말</li>
            </ul>
        </div>

        <div id="con">
            <h4 class="tit-01"><span class="txt-col-01">이음마을학교협동조합은</span>
                “꿈을 잃은 청소년, 마을에서 희망 찾기”라는 소셜미션을 ‘꿈나래 학교(진로체험)’ ‘꿈과 끼 학교(문학예체능 프로젝트 학습)’ ‘마을교사 플랫폼’ 아이템으로 해결하고자 창립하였습니다.</h4>

            <div class="img-box"><img :src="require('@/assets/images/sub/01/02_01.jpg')" alt="마을과 학교 그리고 주민을 잇는 쉼터 공간, 이음마을학교" /></div>

            <p class="bmar_30">
                이음마을학교는 ‘마을이 학교다!’를 기본정신으로 마을의 자원을 이어주는 역할을 담당할 것입니다. 마을 주민들의 재능을 찾아내어 우리 아이들의 교사가 될 수 있도록 지원하겠습니다. 우리 아이들이 마을에서 학습하고 성장해서 다시 우리 마을에 정착하는 진정한 로컬에듀를 지향합니다.
                <br/><br/>
                이음마을학교는 주민들과 참여와 자치의 마을공동체 누구나 꿈을 찾고 민주시민으로 성장하는 마을교육공동체를 만들어 가는데 밑거름이 되겠습니다.
                <br/><br/>
                주민 누구나 함께하는 마을의 자원을 이어주는 이음마을학교를 함께 만들어 주십시오.
            </p>
        </div>
			</div>
    </sub-layout>
  </section>

</template>

<script lang="js">
import SubLayout from '@/components/layouts/SubLayout'
  export default  {
    name: 'greetings',
    props: [],
    mounted() {

    },
    data() {
      return {

      }
    },
    methods: {

    },
    computed: {

    },
    components:{
      SubLayout
    }
}
</script>

<style scoped lang="scss">
  .greetings {

  }
</style>
<style scoped src="@/assets/css/custom.css"></style>