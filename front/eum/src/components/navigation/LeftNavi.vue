<template>
    <section>
        <div id="snb">
            <h2>{{store.state.menuLevel[0].menuName}}</h2>
            <ul>
                <li v-if="store.state.menuLevel[0].menuList" v-for="leftMenu in store.state.menuLevel[0].menuList">
                    <router-link v-if="leftMenu.url" :to="leftMenu.url" :class="active(leftMenu)" ><i class="fa fa-key"></i> <span>{{leftMenu.menuName}}</span></router-link>
                </li>
            </ul>
        </div>
    </section>
</template>

<script lang="js">
export default  {
    name: 'left-navi',
    props: [],
    mounted() {
        $(function(){
            $('.list-group-item').click(function(e) {
                e.preventDefault();
                $('.list-group-item').removeClass('active');
                $(this).addClass('active');
            });
        });
    },
    data() {
        return {
            left_head : require('@/assets/img/menu/left_head.jpg'),
            menu : []
        }
    },
    methods: {
        active : function(leftMenu){
            let secondsLevel = this.store.state.menuLevel[1] || null;
            if(secondsLevel.id == leftMenu.id){
                return "active";
            }
        }
    },
    computed: {

    }
}
</script>

<style scoped lang="scss">
    .left-navi {

    }
</style>
<style scoped src="../../assets/css/custom.css"></style>
