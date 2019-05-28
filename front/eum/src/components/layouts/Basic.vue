<template lang="contactlist-component">

    <section class="basic">
        <Header></Header>
<!--        <Navigation></Navigation>-->
        <slot />
        <Footer></Footer>
    </section>
</template>

<script lang="js">
import Navigation from '@/components/navigation/Navigation.vue'
import Header from '@/components/header/Header.vue'
import Footer from '@/components/footer/Footer.vue'
export default  {
    name: 'basic',
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
    components: {
        Navigation
        ,Header
        ,Footer
    }
}
</script>

<style scoped lang="scss">
    .basic {

    }
</style>
