export default {
    validation : function(notice){
        if(!notice.title){
            alert("제목을 입력해주세요.");
            return true;
        }
    
        if(!notice.content){
            alert("내용을 입력해주세요.");
            return true;
        }
        return false;
    }
    

   
}