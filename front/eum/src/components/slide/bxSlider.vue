<template>
    <div class="slide-box">
        <ul class="gallery-list">
            <li v-for="content in list">
                <router-link :to="`/activity/${content.id}`">
                    <div class="img-box"><img v-if="content.attachImage[0]" :src="`${store.state.basePath}${content.attachImage[0].resourcePath}/s_${content.attachImage[0].virtualName}`" :alt="content.title" /></div>
                    <div class="con-box">
                        <strong>{{content.title}}</strong>
                        <p v-html="content.content"></p>
                    </div>
                </router-link>
            </li>
        </ul>
    </div>
</template>

<script>
    import mainJs from '@/assets/js/main.js'
    export default {
        name: "bxSlider",
        props:['list'],
        mounted() {

        },
        watch : {
            list : function(){
                mainJs.slide();
            }
        }
    }
</script>

<style scoped>

</style>