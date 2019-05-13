<template>
  <div id="app">
    <!-- <Home msg="Welcome to Your Vue.js App"/> -->
    <transition name="page" mode="out-in">
      <router-view :key="$route.fullPath"></router-view>
    </transition>
  </div>
</template>

<script>

export default {
  name: 'app',
  methods:{
  },
  components: {
  }
}
</script>

<style>
#app {
  background: #ffffff;
}

@import url(//fonts.googleapis.com/earlyaccess/nanumgothic.css);
body {
  font-family: 'Nanum Gothic' , sans-serif !important;
}

.page-enter-active, .page-leave-active {
  transition: opacity 0.1s, transform 0.3s;
}
.page-enter, .page-leave-to {
  opacity: 0;
  /* transform: translateX(-100%); */
}
</style>
