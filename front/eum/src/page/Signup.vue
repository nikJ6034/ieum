<template>
    <section>
        <div>
            아이디 : <input type="text" v-model="memberName"> <div>{{memberNameChkMsg}}</div>
        </div>
        <div>
            전화번호 : <input type="text" v-model="mobile">
        </div>
        <div>
            이메일 : <input type="text" v-model="email">
        </div>
        <button @click="signupProsess">회원가입</button>
    </section>
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