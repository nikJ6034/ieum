import Vue from 'vue'
import moment from 'moment'

export default {
    format : function(){
        Vue.filter('formatDate', function(value) {
            if (value) {
                return moment(String(value)).format('YYYY-MM-DD HH:mm');
            }
        });

        Vue.filter('formatDateKo', function(value) {
            if (value) {
                return moment(String(value)).format('YYYY년MM월DD일 HH시mm분');
            }
        });

        Vue.filter('formatDateKo2', function(value) {
            if (value) {
                return moment(String(value)).format('YYYY년MM월DD일');
            }
        });
    }

}