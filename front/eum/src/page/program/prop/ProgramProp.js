export default {
    getKindCode : function(kind){
        if(kind == "narae"){
            return 1;
        }else if(kind == "kki"){
            return  2;
        }else if(kind == "teacher"){
            return 3;
        }
    },
    getKindName : function(kind){
        if(kind == "narae" || kind ==1){
            return "꿈나래 학교";
        }else if(kind == "kki" || kind ==2){
            return  "꿈과 끼 학교";
        }else if(kind == "teacher" || kind ==3){
            return "마을교사 플랫폼";
        }
    },
    getProgramList : function(){
        return [{id:1,name:"꿈나래 학교"},{id:2,name:"꿈과 끼 학교"},{id:3,name:"마을교사 플랫폼"}]
    }
}