(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-aa617f64"],{"35be":function(t,e,i){"use strict";var a=i("3a5a"),n=i.n(a);n.a},"3a5a":function(t,e,i){},e4ab:function(t,e,i){"use strict";i.r(e);var a=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("section",{staticClass:"activity-modify"},[i("sub-layout",[i("div",[i("div",{staticClass:"title"},[i("h4",[i("b",[t._v("활동사진 게시판")])])]),i("form",[i("div",{staticClass:"row"},[i("div",{staticClass:"col-12 subject"},[i("label",[t._v("제목")]),i("input",{directives:[{name:"model",rawName:"v-model",value:t.activity.bbs.title,expression:"activity.bbs.title"}],staticClass:"w-80 d-inline-block form-control",attrs:{type:"text"},domProps:{value:t.activity.bbs.title},on:{input:function(e){e.target.composing||t.$set(t.activity.bbs,"title",e.target.value)}}})]),i("div",{staticClass:"col-12 content"},[null!=t.activity.bbs.content?i("quill",{attrs:{qContent:t.activity.bbs.content},on:{"childs-event":t.parentsMethod}}):t._e()],1),t._l(t.activity.attachImage,function(e){return t.activity.attachImage?i("div",[i("div",[i("img",{staticStyle:{width:"200px"},attrs:{src:""+t.store.state.basePath+e.resourcePath+"/"+e.virtualName}}),t._v("  "),i("button",{staticClass:"btn btn-link",attrs:{type:"button"},on:{click:function(i){return t.deleteFile(e.id)}}},[t._v("삭제")])])]):t._e()}),i("div",{staticClass:"col-12 content"},[i("div",{staticClass:"row"},[i("div",{staticClass:"col-12"},[i("button",{staticClass:"btn btn-info",attrs:{type:"button"},on:{click:t.fileCountAdd}},[t._v("첨부파일 추가")])]),i("div",{staticClass:"col-12"},[t._l(t.fileCount,function(e){return[i("div",{staticStyle:{"margin-top":"5px"}},[i("img",{ref:"file_"+e,refInFor:!0,staticStyle:{width:"200px"}}),t._v("  \n\t\t\t\t\t\t\t\t\t\t\t"),i("input",{attrs:{type:"file",id:"file_"+e,accept:"image/jpeg,image/gif,image/png"},on:{change:t.uploadFile}})])]})],2)])]),i("div",{staticClass:"col-12"},[i("div",{staticClass:"btn-box float-right"},[i("div",["Y"==this.store.state.menuRole.modifyRole?i("button",{staticClass:"btn btn-warning",attrs:{type:"button"},on:{click:t.modify}},[t._v("수정")]):t._e(),"Y"==this.store.state.menuRole.deleteRole?i("button",{staticClass:"btn btn-danger",attrs:{type:"button"},on:{click:t.cancel}},[t._v("취소")]):t._e(),"Y"==this.store.state.menuRole.readRole?i("router-link",{staticClass:"btn btn-info",attrs:{role:"button",to:"/activity"}},[t._v("목록")]):t._e()],1)])])],2)])])])],1)},n=[],s=(i("456d"),i("ac6a"),i("386d"),i("c4f5")),c=i("9ce1"),o={name:"activity-modify",props:[],mounted:function(){this.search()},data:function(){return{activity:{bbs:{title:null,content:null},attachImage:[]},files:{},fileCount:1}},methods:{cancel:function(){this.router.go(-1)},search:function(){var t=this;this.$http.get("".concat(this.store.getters.restWebPath,"/activity/").concat(this.$route.params.id)).then(function(e){var i=e.data;t.activity=i.data})},modify:function(){var t=this;if(window.confirm("수정 하시겠습니까?")){var e=new FormData;e.append("activity",new Blob([JSON.stringify(this.activity)],{type:"application/json"})),Object.keys(this.files).forEach(function(i){e.append("files",t.files[i])}),this.$http.post("".concat(this.store.getters.restWebPath,"/activity/").concat(this.$route.params.id),e,{headers:{"Content-Type":"multipart/mixed"}}).then(function(e){var i=e.data;"success"==i.result?(alert("수정 되었습니다."),t.router.push("/activity/".concat(t.$route.params.id))):alert("수정 되지 않았습니다.")})}},uploadFile:function(){var t=event.target.id,e=this;if(this.files[event.target.id]=event.target.files[0],event.target.files[0]){var i=new FileReader;i.onload=function(i){e.$refs[t][0].src=i.target.result},i.readAsDataURL(event.target.files[0])}},deleteFile:function(t){var e=this;window.confirm("파일을 삭제 하시겠습니까?")&&this.$http.delete("".concat(this.store.getters.restWebPath,"/activity/image/").concat(this.activity.id,"/").concat(t)).then(function(t){if("success"!=t.data.result)return alert(t.data.msg),!1;e.activity.attachImage=t.data.attachFile,alert(t.data.msg)})},parentsMethod:function(t){this.activity.bbs.content=t},fileCountAdd:function(){this.fileCount++}},computed:{},components:{SubLayout:s["a"],Quill:c["a"]}},l=o,r=(i("35be"),i("2877")),u=Object(r["a"])(l,a,n,!1,null,"3e64b202",null);e["default"]=u.exports}}]);
//# sourceMappingURL=chunk-aa617f64.74820b6f.js.map