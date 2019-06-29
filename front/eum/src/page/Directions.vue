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
                <h4 class="tit-01">안녕하세요. 이음 마을학교를 찾아주셔서 진심으로 감사합니다.</h4>

                <strong class="tit-i-01">약도</strong>
                <div id="map" style="width:100%;height:400px;border: solid 2px #ffad55;"></div>
                <br/>
                <strong class="tit-03">이음마을학교</strong>
                <ul class="location-list">
                    <li>
                        <span>주소</span>
                        <p>34406 대전광역시 대덕구 계족산로 5번길 82(대덕구 법동 204-12)</p>
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
      var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
      var options = { //지도를 생성할 때 필요한 기본 옵션
        center: new daum.maps.LatLng(36.365632, 127.431161), //지도의 중심좌표.
        level: 3 //지도의 레벨(확대, 축소 정도)
      };

      var map = new daum.maps.Map(container, options); //지도 생성 및 객체 리턴

      // 마커가 표시될 위치입니다 
      var markerPosition  = new daum.maps.LatLng(36.365660, 127.431150); 

      // 마커를 생성합니다
      var marker = new daum.maps.Marker({
          position: markerPosition
      });

      // 마커가 지도 위에 표시되도록 설정합니다
      marker.setMap(map);

      var iwContent = '<div style="padding:5px 29px">이음 마을학교</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
      iwPosition = new daum.maps.LatLng(36.365632, 127.431161); //인포윈도우 표시 위치입니다

      // 인포윈도우를 생성합니다
      var infowindow = new daum.maps.InfoWindow({
          position : iwPosition, 
          content : iwContent 
      });

      infowindow.open(map, marker); 
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