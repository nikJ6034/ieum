export default {
    validation(activity){
        if(!activity.title){
            alert("제목을 입력해주세요.");
            return true;
        }

        if(!activity.content){
            alert("내용을 입력해주세요.");
            return true;
        }
        return false;
    }
}