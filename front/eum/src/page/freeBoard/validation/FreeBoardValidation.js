export default {
    validation : function(freeBoard){
        if(!freeBoard.title){
            alert("제목을 입력해주세요.");
            return true;
        }

        if(!freeBoard.content){
            alert("내용을 입력해주세요.");
            return true;
        }
        return false;
    }



}