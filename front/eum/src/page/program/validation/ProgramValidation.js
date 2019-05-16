export default {
    validation(programApplication){
        if(programApplication.name == null || programApplication.name == ""){
            alert("이름을 입력해주세요.");
            return true;
        }

        if(programApplication.tel == null || programApplication.tel == ""){
            alert("전화번호를 입력해주세요.");
            return true;
        }
        return false;
    }
}