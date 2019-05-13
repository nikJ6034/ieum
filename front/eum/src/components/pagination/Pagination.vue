<template>
    <section class="pagination d-inline-block">
        <input type="text" v-model="currentPage"/>
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <li class="page-item"><a class="page-link" href="#">1</a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </section>
</template>

<script lang="js">
export default  {
    name: 'pagination',
    props: [],
    mounted() {

    },
    data() {
        return {
            currentPage:0,
            recodes:0,
            perpage:0
        }
    },
    methods: {

    },
    computed: {

    }
}
</script>

<style scoped lang="scss">
    .pagination {

    }
</style>
