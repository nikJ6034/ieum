<template>
    <section class="owl-slide owl-carousel owl-theme">
        <template v-for="image in images">
            <img :src="`${store.state.basePath}${image.resourcePath}/${image.virtualName}`">
        </template>
    </section>
</template>

<script lang="js">
import 'owl.carousel/dist/assets/owl.carousel.css';
import 'owl.carousel/dist/assets/owl.theme.default.css';
import 'owl.carousel';
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
