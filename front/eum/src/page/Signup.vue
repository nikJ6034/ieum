<template>
    <div id="content">
				<div id="navigator">
					<h3>추가 정보입력</h3>
					
				</div>

				<div id="con">

					<table class="info-table">
						<colgroup>
							<col width="20%" />
							<col width="" />
						</colgroup>
						<tbody>
							<tr>
								<th>아이디</th>
								<td class="left"><input type="text" class="intxt ins" v-model="memberName" /><div>{{memberNameChkMsg}}</div></td>
							</tr>
							<tr>
								<th>전화번호</th>
								<td class="left"><input type="text" class="intxt ins" v-model="mobile"/></td>
							</tr>
							<tr>
								<th>이메일</th>
								<td class="left"><input type="text" class="intxt ins" v-model="email" /></td>
							</tr>
						</tbody>
					</table>

					<div class="tmar_30">
                        <button class="btn" @click="signupProsess">회원가입</button>
					</div>


				</div>




			</div>
</template>

<script>
    export default {
        name: "Signup",
        mounted(){

        },
        data(){
            return {
                memberNameChk : false,
                memberNameChkMsg : "아이디를 입력해주세요.",
                memberName : "",
                email : "",
                mobile : "",
                kind : this.$store.state.kind,
                token : this.$store.state.token
            }
        } ,
        methods : {
            signupProsess : function(){
                if(!this.kind || !this.token){
                    alert("소셜 로그인을 하신후에 시도해주세요.");
                    this.$router.push('/login')
                    return;
                }

                if(!this.memberName){
                    alert("아이디를 입력해주세요.");
                    return;
                }
                if(!this.memberNameChk){
                    alert("중복된 아이디 입니다.");
                }

                if(!this.mobile){
                    alert("전화번호를 입력해주세요.");
                    return;
                }
                if(!this.email){
                    alert("이메일 주소를 입력해주세요.");
                    return;
                }

                if(confirm("회원가입을 하시겠습니까?")){
                    let data = {};

                    data.memberName = this.memberName;
                    data.mobile = this.mobile;
                    data.email = this.email;
                    data.token = this.token;
                    data.kind = this.kind;

                    this.$http.post(`${this.store.getters.restWebPath}/signup`
                        ,data
                        // ,{
                        //     headers: {
                        //         'Accept': 'application/json',
                        //         'Content-Type': 'application/x-www-form-urlencoded'
                        //     }
                        // }
                       )
                        .then(({data})=>{
                            if(data.result == "success"){
                                alert(data.msg);
                                this.router.push(`/login`);
                            }else{
                                alert(data.msg);
                            }
                        })
                }

            },
            changMemberName : _.debounce(function(){
                let _this = this;
                this.$http.get(`${this.store.getters.restWebPath}/existsMember`,{params:{memberName: this.memberName}})
                .then(function({data}){
                    if(data.result == "success"){
                        _this.memberNameChk = true;
                    }else{
                        _this.memberNameChk = false;
                    }
                    _this.memberNameChkMsg = data.msg;
                });
            },300)
        },
        watch : {
            memberName : function(value){
                if(value) this.changMemberName();
            }
        }
    }
</script>

<style scoped>

</style>
<style scoped src="@/assets/css/custom.css"></style>