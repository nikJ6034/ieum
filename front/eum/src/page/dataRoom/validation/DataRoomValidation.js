export default {
    validation : function(dataRoom){
        if(!dataRoom.title){
            alert("제목을 입력해주세요.");
            return true;
        }

        if(!dataRoom.content){
            alert("내용을 입력해주세요.");
            return true;
        }
        return false;
    }



}