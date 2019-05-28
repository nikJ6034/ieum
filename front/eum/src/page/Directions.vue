<template>

  <section class="directions">
    <sub-layout>
        <div id="content">
            <div id="navigator">
                <h3>찾아오시는길</h3>
                <ul>
                    <li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
                    <li> > </li>
                    <li>소개</li>
                    <li> > </li>
                    <li>찾아오시는길</li>
                </ul>
            </div>

            <div id="con">
                <h4 class="tit-01">안녕하세요. 이음 마을학교를 찾아주셔서 짐심으로 감사합니다.</h4>

                <strong class="tit-i-01">약도</strong>
                이미지
                <strong class="tit-03">이음마을학교</strong>

                <ul class="location-list">
                    <li>
                        <span>주소</span>
                        <p>34406 대전광역시 대덕구 계족산로 5번길 82(법동)</p>
                    </li>
                    <li>
                        <span>전화</span>
                        <p>042-334-0113</p>
                    </li>
                    <!--<li>
                        <span>팩스</span>
                        <p>042-336-0112</p>
                    </li>-->
                </ul>
            </div>
        </div>
    </sub-layout>
  </section>

</template>

<script lang="js">
import SubLayout from '@/components/layouts/SubLayout'
  export default  {
    name: 'directions',
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
  .directions {

  }
</style>
<style scoped src="@/assets/css/custom.css"></style>