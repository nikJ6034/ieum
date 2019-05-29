<template lang="contactlist-component">

  <section class="banner-list">
    <sub-layout>
	<div id="content">
				<div id="navigator">
					<h3>배너 관리</h3>
					<ul>
						<li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
						<li> > </li>
						<li>관리자</li>
						<li> > </li>
						<li>배너 관리</li>
					</ul>
				</div>

				<div id="con">

					<div class="board-wrap">

						<div class="borad-content">
							<table class="board-con-list">
								<colgroup>
									<col width="">
								</colgroup>
								<thead>
									<tr>
										<th class="num">번호</th>
										<th class="author">제목</th>
										<th>설명</th>
									</tr>
								</thead>
								<tbody>

									<tr v-for="(content, index) in contents">
										<td class="num">{{index+1}}</td>
										<td class="author">
											{{content.description}}
										</td>
										<td class="bod-tit"><router-link :to="{path:`banner/modify/${content.id}`}">{{content.title}}</router-link> </td>
									</tr>
								</tbody>
							</table>

						</div>
					</div>
				</div>
			</div>
    </sub-layout>
  </section>

</template>

<script lang="js">
import SubLayout from '@/components/layouts/SubLayout'
  export default  {
    name: 'banner-list',
    props: [],
    mounted() {
		
		},
	beforeMount(){
		this.search()
	},
    data() {
      return {
		contents : [],
      }
    },
    methods: {
		search : function(pageNum){
			this.$http.get(`${this.store.getters.restWebPath}/banner`,{})
			.then(({data})=>{
				this.contents = data.list;
			})
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
	.banner-list {
	
	}
</style>
<style scoped src="@/assets/css/custom.css"></style>