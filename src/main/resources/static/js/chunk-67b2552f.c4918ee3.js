(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-67b2552f"],{"000c":function(t,i,e){"use strict";var a=e("1914"),n=e.n(a);n.a},1914:function(t,i,e){},"23b3":function(t,i,e){"use strict";e.r(i);var a=function(){var t=this,i=t.$createElement,e=t._self._c||i;return e("section",{staticClass:"activity-insert"},[e("sub-layout",[e("div",[e("div",{staticClass:"title"},[e("h4",[e("b",[t._v("활동사진 게시판")])])]),e("form",[e("div",{staticClass:"row"},[e("div",{staticClass:"col-12 subject"},[e("label",[t._v("제목")]),e("input",{directives:[{name:"model",rawName:"v-model",value:t.activity.bbs.title,expression:"activity.bbs.title"}],staticClass:"w-80 d-inline-block form-control",attrs:{type:"text"},domProps:{value:t.activity.bbs.title},on:{input:function(i){i.target.composing||t.$set(t.activity.bbs,"title",i.target.value)}}})]),e("div",{staticClass:"col-12 content"},[e("quill",{attrs:{qContent:t.activity.bbs.content},on:{"childs-event":t.parentsMethod}})],1),e("div",{staticClass:"col-12 content"},[e("div",{staticClass:"row"},[e("div",{staticClass:"col-12"},[e("button",{staticClass:"btn btn-info",attrs:{type:"button"},on:{click:t.fileCountAdd}},[t._v("첨부파일 추가")])]),e("div",{staticClass:"col-12"},[t._l(t.fileCount,function(i){return[e("div",{staticStyle:{"margin-top":"5px"}},[e("img",{ref:"file_"+i,refInFor:!0,staticStyle:{width:"200px"}}),t._v("  \n\t\t\t\t\t\t\t\t\t\t"),e("input",{attrs:{type:"file",id:"file_"+i,accept:"image/jpeg,image/gif,image/png"},on:{change:t.uploadFile}})])]})],2)])]),e("div",{staticClass:"col-12"},[e("div",{staticClass:"btn-box float-right"},[e("div",["Y"==t.store.state.menuRole.writeRole?e("button",{staticClass:"btn btn-warning",attrs:{type:"button"},on:{click:t.save}},[t._v("저장")]):t._e(),e("button",{staticClass:"btn btn-danger",attrs:{type:"button"},on:{click:t.cancel}},[t._v("취소")]),"Y"==t.store.state.menuRole.readRole?e("router-link",{staticClass:"btn btn-info",attrs:{role:"button",to:"/activity"}},[t._v("목록")]):t._e()],1)])])])])])])],1)},n=[],s=(e("456d"),e("ac6a"),e("c4f5")),o=e("9ce1"),c={name:"activity-insert",props:[],mounted:function(){},data:function(){return{activity:{bbs:{title:"",content:""},attachFile:[]},files:{},fileCount:1}},methods:{cancel:function(){this.router.go(-1)},save:function(){var t=this;if(window.confirm("작성 하시겠습니까?")){var i=new FormData;i.append("activity",new Blob([JSON.stringify(this.activity)],{type:"application/json"})),Object.keys(this.files).forEach(function(e){i.append("files",t.files[e])}),this.$http.post("".concat(this.store.getters.restWebPath,"/activity"),i,{headers:{Accept:"application/json","Content-Type":"multipart/form-data"}}).then(function(i){var e=i.data;"success"==e.result?(alert("작성 되었습니다."),t.router.push("/activity/".concat(e.id))):alert("작성 되지 않았습니다.")})}},uploadFile:function(t){var i=t.target.id,e=this;if(this.files[t.target.id]=t.target.files[0],t.target.files[0]){var a=new FileReader;a.onload=function(t){e.$refs[i][0].src=t.target.result},a.readAsDataURL(t.target.files[0])}},parentsMethod:function(t){this.activity.bbs.content=t},fileCountAdd:function(){this.fileCount++}},computed:{},components:{SubLayout:s["a"],Quill:o["a"]}},l=c,r=(e("000c"),e("2877")),u=Object(r["a"])(l,a,n,!1,null,"5bf8aa24",null);i["default"]=u.exports}}]);
//# sourceMappingURL=chunk-67b2552f.c4918ee3.js.map