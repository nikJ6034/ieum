<template lang="contactlist-component">

  <section class="directions">
    <sub-layout>
      <h4>찾아오시는길</h4>
      <div>
        <div>안녕하세요!</div>
        <div>잘 찾아와주세요~</div>
      </div>
    </sub-layout>
  </section>

</template>

<script lang="js">
import SubLayout from '@/components/layouts/SubLayout'
  export default  {
    name: 'directions',
    props: [],
    mounted() {

    },
    data() {
      return {

      }
    },
    methods: {

    },
    computed: {

    },
    components:{
      SubLayout
    }
}
</script>

<style scoped lang="scss">
  .directions {

  }
</style>
