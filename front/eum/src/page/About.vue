<template>

  <section class="about">
    <sub-layout>
      <div id="content">
				<div id="navigator">
					<h3>마을학교소개</h3>
					<ul>
						<li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
						<li> > </li>
						<li>소개</li>
						<li> > </li>
						<li>소개</li>
					</ul>
				</div>

				<div id="con">
					<h4 class="tit-01">이음마을학교 협동조합은 마을 주민들이 협동하여 마을의 자원을 잇고 이어서 <span class="txt-col-01">살고 싶은 마을, 아이들이 행복한 마을</span><br/>
					마을 교육 공동체를 만들어 갑니다.</h4>

					<strong class="tit-i-01">마을</strong>
					<div class="line-box">
						초극단적 이중위험사회(Ultra–super Dual Risk Society)로 치닫고 있는 한국의 현실에서 주민들의 따뜻한 삶의 안식처이자 이웃과 더불어 사는
						공동체적 삶이 구현되는 곳
					</div>

					<strong class="tit-i-01">마을공동체</strong>
					<div class="line-box">
						공동체적 삶의 가치와 이를 구현하고자하는 마을의 특성을 강조하는 용어로써, 공동체적 삶의 방식을 통해 초극단적 이중위험사회
						(Ultra–super Dual Risk Society)로 치닫고 있는 한국의 현실을 극복하고자하는 상호 호혜적 신뢰와 협동의 네트워크 체계
					</div>

					<strong class="tit-i-01">마을교육공동체 구현체로써의 이음터의 지향성</strong>
					<div class="line-box">
						<ul class="list-i-01">
							<li>
								<strong class="txt-col-01">교육 내용적 측면</strong>
								<p>기존의 교육제도와 방식을 극복하고자하는 의지의 산물로써, 교육에 학교- 학생- 학부모 3주체 중심의 모형이 아니라, 마을과 지역사회를
									끌어들여 교육의 질과 폭을 넓히고자 하는 의지가 반영된 모형. 학교- 학생- 학부모- 지역사회(마을) 연계모형(linkage model)</p>
							</li>
							<li>
								<strong class="txt-col-01">교육 가치적 측면</strong>
								<p>초극단적 이중위험사회(Ultra–super Dual Risk Society)로 치닫고 있는 한국의 현실에서 우리의 아이들과 주민들이 이웃과 더불어 함께 사는
									공동체적 삶의 가치가 중요하다는 것을 인식하고 체험하는 경험적 학습 모델(experiential learning model)</p>
							</li>
							<li>
								<strong class="txt-col-01">교육 내용적 측면</strong>
								<p>초극단 이중위험사회(Ultra–super Dual Risk Society)를 극복하기 위해 마을의 가치와 공동체적 가치를 구현함에 있어서 교육 부문의
									중요성을 강조하고 교육적 요소와 대안을 적극적으로 활용코자하는 특화 모형(specialized model)</p>
							</li>
						</ul>
					</div>




				</div>




			</div>
    </sub-layout>
  </section>

</template>

<script lang="js">
import SubLayout from '@/components/layouts/SubLayout'
  export default  {
    name: 'about',
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
  .about {

  }
</style>
<style scoped src="@/assets/css/custom.css"></style>