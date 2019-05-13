<template>
    <section class="sub-layout">
        <Header></Header>
        <Navigation></Navigation>
        <div>
            <div class="row" style="margin:0px;">
                <div class="col-lg-3 d-none d-lg-block">
                    <LeftNavi></LeftNavi>
                </div>
                <div class="col-12 col-lg-9">
                    <slot />
                </div>
            </div>
        </div>
        <Footer></Footer>
    </section>
</template>

<script lang="js">
import Navigation from '@/components/navigation/Navigation.vue'
import Header from '@/components/header/Header.vue'
import LeftNavi from '@/components/navigation/LeftNavi.vue'
import Footer from '@/components/footer/Footer.vue'
export default  {
    name: 'sub-layout',
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
        ,LeftNavi
    }
}
</script>

<style scoped lang="scss">
    .sub-layout {

    }
</style>
