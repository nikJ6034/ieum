<template>
  <section class="quill">
    <template v-if="checkIE">
    <textarea v-model="content" class="form-control"></textarea>
  </template>
  <template v-if="!checkIE">
    <vue-editor v-model="content" :html="content" useCustomImageHandler @imageAdded="handleImageAdded" :editorOptions="editorSettings"></vue-editor>
  </template>
  </section>
</template>

<script lang="js">
// import Quill from 'quill'
// import { quillEditor } from 'vue-quill-editor'
import { VueEditor, Quill } from 'vue2-editor';
// import { ImageDrop } from 'quill-image-drop-module'
// import ImageResize from 'quill-image-resize-module'
import ImageResize from '@/modules/quill-image-resize-module/src/ImageResize';
// import ImageResize from '@/modules/quill-image-resize-module'
// Quill.register('modules/imageDrop', ImageDrop)
Quill.register('modules/imageResize', ImageResize);

export default  {
  name: 'quill',
  props: ['qContent'],
  mounted() {

  },
  data() {
    return {
      editorSettings: {
        modules: {
//          imageDrop: true,
          imageResize: {}
        }
      },
       content: this.qContent
    }
  },
  methods: {
    handleImageAdded: function(file, Editor, cursorLocation, resetUploader) {
      // An example of using FormData
      // NOTE: Your key could be different such as:
      // formData.append('file', file)
      var formData = new FormData();
      formData.append('files', file);
        this.$http.post(`${this.store.getters.restWebPath}/resource/img`, formData,{
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'multipart/form-data'
          }
        })
        .then((result) => {
          Editor.insertEmbed(cursorLocation, 'image', `${this.store.state.basePath}${result.data[0].resourcePath}/${result.data[0].virtualName}`);
          resetUploader();
        })
        .catch(() => {
          alert("이미지 업로드에 실패하였습니다.");
        })
//        Editor.insertEmbed(cursorLocation, 'image', "https://kr.vuejs.org/images/logo.png");
    }
  },
  watch: {
    content : function(){
      this.$emit('update:qContent',this.content);
    }
  },
  computed: {

    checkIE : function(){
      let agent = window.navigator.userAgent.toLowerCase();

      if ( (navigator.appName === 'Netscape' && navigator.userAgent.search('Trident') !== -1) || (agent.indexOf("msie") !== -1) ) {
        return true;
      }else {
        return false;
      }
    }
  },
  components:{
    VueEditor
  }
}
</script>

<style scoped lang="scss">
  .quill {

  }
</style>
