<template>
    <section class="sub-layout">
        <Header></Header>
<!--        <Navigation></Navigation>-->
        <div id="spot">
            <div class="con-area">
                <strong><span>학생중심</span>의 사랑과 자발성 교육</strong>
                <p>소통과 나눔의 마을공동체 이음마을학교 입니다.</p>
            </div>
        </div>
        <div id="layout">
            <LeftNavi></LeftNavi>
            <slot />
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
<style scoped src="@/assets/css/custom.css"></style>
