export default {
    validation(activity){
        if(!activity.bbs.title){
            alert("제목을 입력해주세요.");
            return true;
        }

        if(!activity.bbs.content){
            alert("내용을 입력해주세요.");
            return true;
        }
        return false;
    }
}