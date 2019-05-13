export default {
    validation : function(notice){
        if(!notice.bbs.title){
            alert("제목을 입력해주세요.");
            return true;
        }
    
        if(!notice.bbs.content){
            alert("내용을 입력해주세요.");
            return true;
        }
        return false;
    }
    

   
}