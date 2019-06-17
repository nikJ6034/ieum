<template>
    <section class="program-modify">
        <sub-layout>
            <div id="content">
				<div id="navigator">
					<h3>{{kindName}}</h3>
					<ul>
						<li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
                        <li> > </li>
                        <li>프로그램</li>
                        <li> > </li>
                        <li>프로그램</li>
					</ul>
				</div>

				<div id="con">

					<div class="board-wrap">

						<div class="board-view-header" style="border-bottom:1px solid #ddd;">
							<ul class="board-view-tit">
								<li class="title">{{program.title}}</li>
							</ul>
						</div>


                        <div class="board-add-header" style="border-bottom:1px solid #ddd;">

							<ul>
								<li class="li01">일자</li>
								<li class="li05">
									<p class="con-txt">{{program.strDate|formatDateKo2}}~{{program.endDate|formatDateKo2}}</p>
								</li>
								<li class="li01">신청일</li>
								<li class="li05">
									<p class="con-txt">{{program.strAppDate|formatDateKo2}}~{{program.endAppDate|formatDateKo2}}</p>
								</li>
                                <li class="li01">장소</li>
								<li class="li05">
									<p class="con-txt">{{program.address}} {{program.addressDetail}}</p>
								</li>
							</ul>
                            <ul>
                                <li class="li05">
									<p class="con-txt"><img v-if="program.imageFile" :src="`${store.state.basePath}${program.imageFile.resourcePath}/${program.imageFile.virtualName}`" style="height:150px;"/></p>
								</li>
                            </ul>
						</div>

                        <div class="board-add-header" style="border-bottom:1px solid #ddd;">

							<ul>
								<li class="li01">이름</li>
								<li class="li05">
									<p class="con-txt"><input type="text" readonly v-model="programApplication.name"></p>
								</li>
								<li class="li01">전화번호</li>
								<li class="li05">
									<p class="con-txt"><input type="text" v-model="programApplication.tel"></p>
								</li>
                                <li class="li01">특이사항</li>
								<li class="li04">
									<p class="con-txt"><textarea style="width:80%;" rows="5" v-model="programApplication.etc"></textarea></p>
								</li>
							</ul>
                            
						</div>

						<!-- 버튼 -->
						<div class="board-btn-list">
                            <button v-if="isExpDate" type="button" class="btn" @click="goApplication">신청</button>
                            <router-link role="button" class="btn li02" :to="`/program/${this.kind}`">목록</router-link>
						</div>
						<!-- //버튼 -->
						<!-- //컨텐트 -->
					</div>
				</div>
			</div>
        </sub-layout>
    </section>
</template>

<script>
import SubLayout from '@/components/layouts/SubLayout'
import ProgramValidation from "./validation/ProgramValidation";
import ProgramProp from "./prop/ProgramProp"
export default {
    name: "ProgramDetail",
    props : ["kind","id"],
    mounted() {
        this.program.kind = ProgramProp.getKindCode(this.kind);
        this.kindName = ProgramProp.getKindName(this.kind);
        this.search();
    },
    data() {
        return {
            program : {
                id: null, kind : 1, address : "", addressDetail : "", addressNumber : null
                , strDate : "", endDate : "" ,strAppDate : null, endAppDate : null
                ,title:"", content:"",attachFile:[], imageFile: null, openType : "M"
            },
            programApplication : {memberId: this.store.state.ieumUserId, name:this.store.state.ieumUserName, tel:"", program: {id: null}, etc:""},
            kindName : ""
        }
    },
    methods: {
        search : function(){
            this.$http.get(`${this.store.getters.restWebPath}/program/${this.id}`)
                .then(({data})=>{
                    this.program = data.data;
                    this.programApplication.program.id = data.data.id;
                })
        },
        goApplication : function(){
            if(ProgramValidation.validation(this.programApplication)) return;

            if(confirm("신청하시겠습니까?")){
                this.$http.post(`${this.store.getters.restWebPath}/programApp` ,this.programApplication)
                    .then(({data})=>{
                        if(data.result == "success"){
                            alert(data.msg);
                            this.router.push(`/program/${this.kind}/${this.id}`);
                        }else{
                            alert(data.msg);
                        }
                    })
            }

            //alert(123);
            //this.router.push({path:`/program/${this.kind}/application/${this.id}`});
        }
    },
    computed: {
        isExpDate : function(){
            let strAppDate = new Date(this.program.strAppDate).getTime();
            let endAppDate = new Date(this.program.endAppDate).getTime();
            let curentTime = new Date().getTime();
            if(strAppDate <= curentTime && curentTime <=endAppDate){
                return true;
            }else{
                return false;
            }
        }
    },
    components: {
        SubLayout
    }
}
</script>

<style scoped>
    .program-modify {

    }
</style>
<style scoped src="@/assets/css/custom.css"></style>