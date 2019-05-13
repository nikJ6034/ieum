<template lang="contactlist-component">

  <section class="banner-list">
    <sub-layout>
      <h4><b>배너 관리</b></h4>
		<table class="table table-striped table-bordered">
		<colgroup>
			<col width="30%">
			<col width="70%">
		</colgroup>
		<thead class="thead-dark text-center">
			<tr>
				<th scope="col">제목</th>
				<th scope="col">설명</th>
			</tr>
		</thead>
		<tbody>
			<tr v-for="(content, index) in contents">
				<td><router-link :to="{path:`banner/modify/${content.id}`}">{{content.title}}</router-link></td>
				<td>
					{{content.description}}
				</td>
			</tr>
		</tbody>
	</table>
	
	<div>
		<div class="btn-box float-right">
			<div>
<!-- 					<button v-if="this.store.state.menuRole.writeRole=='Y'" type="button" class="btn btn-warning" @click="goInsertPage">작성</button> -->
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
  
	.searchDiv {
		padding: 20px;
		/*  border: solid 1px #ddd; */
		background: #71beff;
		border-radius: 10px;
		box-shadow: 4px 3px 11px 2px grey;
		margin: 0 0 20px 0;
	}
	
	.searchDiv .totalRows{
		margin-left: 10px
	}

	.table .thead-dark th{
		background-color: #67708b;
    border-color: #67708b;
	}
  
	.page-item.active .page-link{
		background-color: #a6b4c4;
		border-color : #a6b4c4;
	}
	
	.btn-box button,a {
		margin: 0 10px 10px 0;
	}
</style>
