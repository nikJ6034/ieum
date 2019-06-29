<template>

  <section class="history">
    <sub-layout>
      <div id="content">
            <div id="navigator">
                <h3>연혁</h3>
                <ul>
                    <li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
                    <li> > </li>
                    <li>소개</li>
                    <li> > </li>
                    <li>연혁</li>
                </ul>
            </div>

            <div id="con">
                <h4 class="tit-01">이음마을학교 협동조합은 마을 주민들이 협동하여 마을의 자원을 잇고 이어서 <span class="txt-col-01">살고 싶은 마을, 아이들이 행복한 마을</span><br/>
                마을 교육 공동체를 만들어 갑니다.</h4>

                <strong class="tit-i-01">연혁</strong>

                <ul class="history-list">
                    <li>
                        <strong class="tit">2018</strong>
                        <ul>
                            <li><span>12.14</span><p>이음마을학교 협동조합 창립총회 (이사장 박종갑 이사 김성학, 이민영, 인혜란, 최승희 감사 정민채)</p></li>
                        </ul>
                    </li>

                    <li>
                        <strong class="tit">2019</strong>
                        <ul>
                            <li><span>01.15</span><p>창립기념 특강1 “우리아이 첫 입학, 자녀교육 길라잡이”(강사- 강정숙 대전동서초등학교 교사)</p></li>
                            <li><span>01.23</span><p>이음마을학교협동조합 법인등록</p></li>
                            <li><span>02.14</span><p>이음마을학교협동조합 사업자등록</p></li>
                            <li><span>02.21</span><p>창립기념 특강2 “우리아이의 행복한 책읽기, 어떻게 도와줄까?”(강사-조현희 대전탄방초등학교 교사)</p></li>
                            <li><span>03.06</span><p>한국사회적기업진흥원 2019년 사회적기업가 육성사업 협약 체결</p></li>
                            <li><span>03.22</span><p>이음마을학교협동조합 개소식</p></li>
                            <li><span>04.18</span><p>창립기념 특강3 “우리 아이 스마트폰 사용, 어떻게 봐야 하나요?”(강사-김현지 학생상담센터 강사)</p></li>
                            <li><span>05.02</span><p>마을교사 아카데미 “영상제작 기초과정” 1강(~5.30까지 매주 목요일)</p></li>
                            <li><span>05.08</span><p>페토사회적협동조합과 업무협약 체결</p></li>
                            <li><span>05.30</span><p>마을교사 아카데미 "영상제작 기초과정" 수료식</p></li>
                            <li><span>06.13</span><p>마을교사 아카데미 "목공기초과정" 1강(~7.18까지 매주 목요일)</p></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </sub-layout>
  </section>

</template>

<script lang="js">
import SubLayout from '@/components/layouts/SubLayout'
  export default  {
    name: 'history',
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
  .history {

  }
</style>
<style scoped src="@/assets/css/custom.css"></style>