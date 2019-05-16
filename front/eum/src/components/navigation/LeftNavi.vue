<template>
    <section class="left-navi">
        <div class="w250 h150 left-navi-head" :style="{ backgroundImage: `url('${left_head}')` }">
            {{store.state.menuLevel[0].menuName}}
        </div>
        <div class="list-group">
            <template v-if="store.state.menuLevel[0].menuList" v-for="leftMenu in store.state.menuLevel[0].menuList">
                <router-link v-if="leftMenu.url" :to="leftMenu.url" :class="'list-group-item '+ active(leftMenu)" ><i class="fa fa-key"></i> <span>{{leftMenu.menuName}}</span></router-link>
            </template>
            <!-- <a href="#" class="list-group-item active"><i class="fa fa-key"></i> <span>학교소개</span></a>
            <a href="#" class="list-group-item"><i class="fa fa-credit-card"></i> <span>인사말</span></a>
            <a href="#" class="list-group-item"><i class="fa fa-question-circle"></i> <span>찾아오시는길</span></a>
            <a href="#" class="list-group-item"><i class="fa fa-arrow-circle-o-left"></i> <span>연혁</span></a> -->
        </div>
    </section>
</template>

<script lang="js">
export default  {
    name: 'left-navi',
    props: [],
    mounted() {
        this.$(($)=>{
            $('.list-group-item').click(function(e) {
                e.preventDefault();
                $('.list-group-item').removeClass('active');
                $(this).addClass('active');
            });
        })
    },
    data() {
        return {
            left_head : require('@/assets/img/menu/left_head.jpg'),
            menu : []
        }
    },
    methods: {
        active : function(leftMenu){
//          list-group-item
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

    .left-navi-head{
        margin:auto;
        border-radius: 10px;
        background: #d2d1d1;
        text-align: center;
        font-size: 30px;
        line-height: 150px;
    }

    .list-group {
        // float:left;
        // padding-top:20px;
        width:250px;
        margin: 20px auto;
    }
    .lead {
        margin:auto;
        left:0;
        right:0;
        padding-top:10%;
    }

    .list-group-item.active{
        background-color: #b5bcc4;
        border-color: #b5bcc4;
    }

    .list-group-item{
        color: #000000;
    }
</style>
