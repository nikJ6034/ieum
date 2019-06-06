<template>
    <section class="program-insert">
        <sub-layout>
            <div id="content">
				<div id="navigator">
					<h3>프로그램</h3>
					<ul>
						<li><img :src="require('@/assets/images/custom/navi_home_i.png')" alt="home" /></li>
						<li> > </li>
						<li>관리자</li>
						<li> > </li>
						<li>프로그램</li>
					</ul>
				</div>

				<div id="con">

					<div class="board-wrap">

						<div class="board-add-header" style="border-bottom:1px solid #ddd;">

                            <ul>
								<li class="li01">프로그램</li>
								<li class="li05 ch">
									<select class="select" name="kind" v-model="program.kind">
                                        <option v-for="pro in programKindList" :value="pro.id">{{pro.name}}</option>
                                    </select>
								</li>
							</ul>
                            
							<ul>
								<li class="li01">제목</li>
								<li class="li04">
									<input type="text" v-model="program.title" class="tit-int">
								</li>
							</ul>

							<ul>
								<li class="li01">일자</li>
								<li class="li05">
									<datepicker :language="ko" v-model="program.strDate" :format="customFormatter"></datepicker>~
                                    <datepicker :language="ko" v-model="program.endDate" :format="customFormatter"></datepicker>
								</li>
								<li class="li01">신청기간</li>
								<li class="li05">
									<datepicker :language="ko" v-model="program.strAppDate" :format="customFormatter"></datepicker>~
                                    <datepicker :language="ko" v-model="program.endAppDate" :format="customFormatter"></datepicker>
								</li>
							</ul>
						</div>
                        <div class="board-add-header" style="border-bottom:1px solid #ddd;">
                            <ul>
								<li class="li01">우편번호</li>
                                <li class="li05">
                                    <input class="tit-int" type="text" v-model="program.addressNumber" readonly>
                                </li>
                                <li>
                                    <button type="button" class="btn" @click="postPopup">우편번호 검색</button>
                                </li>
                                
                            </ul>
                            <ul>
                                <li class="li01">주소</li>
                                <li>
                                    <input class="tit-int" v-model="program.address" type="text" readonly>
                                </li>
                                <li class="li01">상세 주소</li>
                                <li>
                                    
                                    <input class="tit-int" type="text" v-model="program.addressDetail">
                                </li>
                            </ul>
                        </div>
					    <div class="board-add-header" style="border-bottom:1px solid #ddd;">
                            <ul>
                                <li class="li01">이미지파일</li>
                                <li>
                                     <input type="file"  id="image" v-on:change="uploadImage">
                                </li>
                            </ul>
                        </div>
                        <div class="border-view-con">
                            <quill :qContent.sync="program.content"></quill>
                        </div>
                        <div>
                            <button type="button" class="btn" @click="fileCountAdd">첨부파일 추가</button>
                        </div>
						<div class="board-file-box">
							<table class="board-file-list">
								<tbody>

								<tr v-for="n in fileCount">
									<th class="title">첨부파일</th>
									<td>
										<ul>

											<li>
												<img :ref="`file_${n}`" style="width:200px;"/>
											</li>
                                            <li>
                                                <input type="file" :id="`file_${n}`" accept='image/jpeg,image/gif,image/png' v-on:change="uploadFile">
											</li>

										</ul>
									</td>
								</tr>
								
								</tbody>
							</table>
						</div>
						<!-- 버튼 -->
						<div class="board-btn-list">
                            <button v-if="store.state.menuRole.writeRole=='Y'" type="button" class="btn" @click="save">저장</button>
                            <button type="button" class="btn li03" @click="cancel">취소</button>
                            <router-link v-if="store.state.menuRole.readRole=='Y'" role="button" class="btn li02" to="/admin/program">목록</router-link>
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
import SubLayout from '@/components/layouts/SubLayout';
import Quill from '@/components/editor/quill/Quill';
import Datepicker from 'vuejs-datepicker';
import {en, ko} from 'vuejs-datepicker/dist/locale';
import moment from 'moment';
import ProgramProp from "../../program/prop/ProgramProp.js"

export default {
    name: "ProgramInsert",
    mounted() {
        this.program.kind = this.$route.query.kind
        this.programKindList = ProgramProp.getProgramList();
    },
    data() {
        return {
            program : {
                kind : 1, address : "", addressDetail : "", addressNumber : null
                , strDate : "", endDate : "" ,strAppDate : "", endAppDate : ""
                ,title:"", content:"",attachFile:[], imageFile: null, openType : "M"
            },
            files : {},
            fileCount : 1,
            image : null,
            en: en,
            ko: ko,
            programKindList : []
        }
    },
    methods: {
        cancel : function(){
            this.router.go(-1);
        },
        save : function(){
            //if(programValidation.validation(this.program)) return; //값 검증
            if(window.confirm("작성 하시겠습니까?")){
                let formData = new FormData();
                formData.append("program",new Blob([JSON.stringify(this.program)], {
                    type: "application/json"
                }));
                Object.keys(this.files).forEach((key)=>{
                    formData.append("files",this.files[key]);
                })

                formData.append("image", this.image);

                this.$http.post(`${this.store.getters.restWebPath}/program`
                    ,formData
                    ,{
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'multipart/form-data'
                        }
                    })
                    .then(({data})=>{
                        if(data.result == "success"){
                            alert("작성 되었습니다.");
                            this.router.push(`/admin/program/${data.id}`);
                        }else{
                            alert("작성 되지 않았습니다.");
                        }
                    })
            }
        },
        uploadFile : function(event){
            let targetId = event.target.id;
            let _this = this;
            this.files[event.target.id]=event.target.files[0];

            if (event.target.files[0]) {
                let reader = new FileReader();
                reader.onload = function (e) {
                    _this.$refs[targetId][0].src = e.target.result;
                }
                reader.readAsDataURL(event.target.files[0]);
            }
        },
        fileCountAdd : function(){
            this.fileCount++;
        },
        uploadImage : function(event){
            this.image = event.target.files[0];
        },
        customFormatter(date) {
            return moment(date).format('YYYY-MM-DD');
        },
        postPopup(){
            let _this = this;
            new daum.Postcode({
                oncomplete: function(data) {
                    // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                    // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                    // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                    var addr = ''; // 주소 변수
                    var extraAddr = ''; // 참고항목 변수

                    //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                    if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                        addr = data.roadAddress;
                    } else { // 사용자가 지번 주소를 선택했을 경우(J)
                        addr = data.jibunAddress;
                    }

                    // 우편번호와 주소 정보를 해당 필드에 넣는다.
                    _this.program.addressNumber = data.zonecode;
                    _this.program.address = addr;
                }
            }).open();
        }
    },
    computed: {

    },
    components: {
        SubLayout,
        Quill,
        Datepicker
    }
}
</script>

<style scoped>
    .program-insert {

    }
</style>
<style scoped src="@/assets/css/custom.css"></style>