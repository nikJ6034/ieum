<template>
    <section class="reservationMng">
        <sub-layout>
            <div id="content">
                <div id="con">
                    <div class="cal-wrap">
                        <full-calendar
                        v-if="store.state.ieumUserId"
                        ref="calendar"
                        :selectable="selectable"
                        :event-sources="eventSources"
                        :config="config"
                        @view-render="viewRender"
                        @event-created="eventSelected"
                        ></full-calendar>

            <ul class="cation-list">
                <li class="li01">신청대기</li>
                <li class="li02">신청완료</li>
            </ul>

        <div class="data-box">
            <table class="info-table">
            <colgroup>
                <col width="20%">
                <col width="30%">
                <col width="10%">
                <col width="20%">
                <col width="10%">
                <col width="10%">
            </colgroup>
            <thead class="thead-dark text-center">
                <tr>
                    <td>시간</td>
                    <td>사유</td>
                    <td>신청자</td>
                    <td>전화번호</td>
                    <td>승인</td>
                    <td>취소</td>
                    </tr>
            </thead>
            <tbody>
                <tr v-for="reserv in reserveList">
                    <td class=text-center>{{reserv.start|formatDateKo}}<br/>{{reserv.end|formatDateKo}}</td>
                    <td>{{reserv.reason}}</td>
                    <td>{{reserv.member.name}}</td>
                    <td>{{reserv.tel}}</td>
                    <td class=text-center>
                        <template v-if="reserv.deleteYN == 'N'">
                            <template v-if="reserv.acceptYN == 'N'">
                                <button type="button" class="btn" @click="acceptReserv(reserv.id)">승인</button>
                            </template>
                            <template v-if="reserv.acceptYN == 'Y'">
                                <button type="button" class="btn li03" @click="acceptReserv(reserv.id)">취소</button>
                            </template>
                        </template>
                    </td>
                    <td class=text-center>
                        <template v-if="reserv.deleteYN == 'N'">
                            <button type="button" class="btn li04" @click="deleteReserv(reserv.id)">삭제</button>
                        </template>
                        <template v-else>
                            <button type="button" class="btn" @click="deleteReserv(reserv.id)">복구</button>
                        </template>
                    </td>
                </tr>
            </tbody>
            </table>
        </div>
                    </div>
                </div>
            </div>

    </sub-layout>
    </section>

</template>

<script lang="js">
import 'fullcalendar/dist/fullcalendar.css'
import SubLayout from '@/components/layouts/SubLayout'
import { FullCalendar } from 'vue-full-calendar'
import moment from 'moment';
let _this = null;
  export default  {
    name: 'reservationMng',
    props: [],
    beforeMount() {
        _this = this;
    },
    data() {
        return {
            eventSources: [
                {
                  events(start, end, timezone, callback) {

                    _this.$http.get(`${_this.store.getters.restWebPath}/reservation`,{params:{startDate:start.format(), endDate:end.format()}})
                    .then(({data})=>{
                    let dataList = [];
                    _this.reserveList = data.list;
                    data.list.forEach(function(data){
                        if(data.regId !=_this.store.state.ieumUserId){
                            data.color="#61db5f";
                        }else if(data.acceptYN=="N"){
                            data.color="#e36c6c";
                        }else{
                            data.color="#5a5afa";
                        }

                        data.start = moment(String(data.start));
                        data.end = moment(String(data.end));

                        if(data.deleteYN=="N"){
                            dataList.push(data);
                        }
                    })
                        callback(dataList);
                    })

                  },
                  color: 'yellow',
                  textColor: 'black'
                },
            ],
              config: {
                locale: 'ko',
                minTime:'09:00:00',
                allDaySlot : false,
                slotEventOverlap   : false,	//슬롯 중복 금지 옵션
                eventStartEditable  : false, //시작일 변경 금지 옵션
                eventDurationEditable  : false //기간 변경 금지 옵션
              },
              selectable:true,
              header : {
                  left:   'prev',
                  center: 'title',
                  right:  'dayGridMonth,timeGridWeek,timeGridDay,next'
              },
             reservation : {
                    title : this.store.state.ieumUserName,
                    start : '',
                    end : '',
                    allDay : false,
                    tel : '',
                    reason: ''
                },
            caleandar : {
                startDate : null,
                endDate : null
            },
            reserveList : []
        }
    },
    methods: {
        eventSelected : function(event){
            this.reservation.start = new Date(event.start.format());
            this.reservation.end = new Date(event.end.format());
        },
        viewRender : function(view, element){
        this.caleandar.startDate = view.intervalStart.format();
        this.caleandar.endDate = view.intervalEnd.format();
            if(view.name == 'month'){
            view.options.selectable = false; //월별 달력은 예약일 선택 금지.
            }else{
            view.options.selectable = true;
            }

        },
        acceptReserv : function(id){
        if(confirm("진행하시겠습니까?")){
                this.$http.put(`${this.store.getters.restWebPath}/reservation/accept/${id}`)
                .then(({data})=>{
                    alert(data.msg);
                    this.$refs.calendar.$emit('refetch-events')
                });
            }
        },
        deleteReserv : function(id){
            if(confirm("진행하시겠습니까?")){
                this.$http.delete(`${this.store.getters.restWebPath}/reservation/${id}`)
                .then(({data})=>{
                    alert(data.msg);
                    this.$refs.calendar.$emit('refetch-events')
                });
            }

        }
    },
    computed: {

    },
    components:{
        SubLayout,
        FullCalendar
    }
}
</script>

<style scoped lang="scss">
  .reservationMng {

  }
</style>
<style scoped src="@/assets/css/custom.css"></style>