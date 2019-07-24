<template>
    <section class="owl-slide owl-carousel owl-theme">
        <viewer  v-for="image in images">
               <img :src="`${store.state.basePath}${image.resourcePath}/${image.virtualName}`" width="300px" height="246px">
        </viewer >
    </section>
</template>

<script lang="js">
import 'owl.carousel/dist/assets/owl.carousel.css';
import 'owl.carousel/dist/assets/owl.theme.default.css';
import 'owl.carousel';
import 'viewerjs/dist/viewer.css'
import Viewer from 'v-viewer'
import Vue from 'vue'
Vue.use(Viewer);
export default  {
    name: 'owl-slide',
    props: {images:Array, options:Object},
    mounted() {
        let $el = $(this.$el);
        $el.owlCarousel(this.options);
    },
    data() {
        return {

        }
    },
    methods: {
    },
    computed: {

    }
}
</script>

<style scoped lang="scss">
    .owl-slide {

    }
</style>
