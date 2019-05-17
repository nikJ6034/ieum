export default {
    validation : function(dataRoom){
        if(!dataRoom.bbs.title){
            alert("제목을 입력해주세요.");
            return true;
        }

        if(!dataRoom.bbs.content){
            alert("내용을 입력해주세요.");
            return true;
        }
        return false;
    }



}