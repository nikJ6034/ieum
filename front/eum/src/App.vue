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

.page-leave-active,
.page-enter-active {
  transition: 0.3s;
}
.page-enter {
  /*transform: translate(-100%, 0);*/
  opacity: 0;
}

.page-leave-active {
  /*transform: translate(100%, 0);*/
  opacity: 0;
}

</style>
