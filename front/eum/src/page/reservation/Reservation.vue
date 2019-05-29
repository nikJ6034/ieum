<template lang="contactlist-component">

  <section class="reservation">
    <sub-layout>
      <div id="content">
        <div id="con">
        <div class="cal-wrap">
          <full-calendar id='calendar'
          v-if="store.state.ieumUserId"
          ref="calendar"
          :selectable="selectable"
          :event-sources="eventSources"
          :config="config" 
          @view-render="viewRender" 
          @event-created="eventSelected">
          </full-calendar>
          <ul class="cation-list">
							<li class="li01">신청대기</li>
							<li class="li02">신청완료</li>
						</ul>

						<div class="cal-add">
							<div class="con-h">
								<strong class="tit">신청하기</strong>
							</div>
							<div class="con-box">
								<ul class="cal-form">
									<li>
										<div class="tit"><span>*</span>예약시작일</div>
										<div class="con-con">
											<span id="start-date" v-if="reservation.start">{{reservation.start|formatDateKo}}</span>~
											<span id="end-date" v-if="reservation.start">{{reservation.end|formatDateKo}}</span>
										</div>
									</li>
									<li>
										<div class="tit"><span>*</span>신청사유</div>
										<div class="con-con">
											<textarea class="textarea" v-model="reservation.reason"></textarea>
										</div>
									</li>
									<li>
										<div class="tit"><span>*</span>연락처</div>
										<div class="con-con">
											<input type="text" class="intxt" v-model="reservation.tel"/>
										</div>
									</li>
								</ul>

								<p class="in-txt">*는 필수항목 입니다.</p>

								<div class="center"><button type="button" class="btn" @click="save">대관신청</button></div>
							</div>
						</div>

            <div class="data-box">
              <table class="info-table">
                <thead>
                  <tr>
                    <th>시간</th>
                    <th>사유</th>
                    <th>상태</th>
                    <th>취소</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="reserv in ownList">
                    <td>{{reserv.start|formatDateKo}}<br/>{{reserv.end|formatDateKo}}</td>
                    <td>{{reserv.reason}}</td>
                    <td>{{(reserv.acceptYN == "N")?"대기":"승인"}}</td>
                    <td>
                      <template v-if="reserv.acceptYN == 'N'">
                        <button type="button" class="btn btn-danger" @click="deleteReserv(reserv.id)">취소</button>
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
    name: 'reservation',
    props: [],
    beforeMount() {
      _this = this;
    moment.locale("Asia/Seoul");
    },
    data() {
        return {
          eventSources: [
            {
                   events(start, end, timezone, callback) {
                   _this.$http.get(`${_this.store.getters.restWebPath}/reservationByOwn`,{params:{startDate:start.format(), endDate:end.format()}})
               .then(({data})=>{
                 _this.ownList = data.list;
               })
               
                   _this.$http.get(`${_this.store.getters.restWebPath}/reservation`,{params:{startDate:start.format(), endDate:end.format()}})
               .then(({data})=>{
            let dataList = [];
            _this.contents = data.list;
                 data.list.forEach(function(data){
                   if(data.member.id !=_this.store.state.ieumUserId){
                     data.color="#61db5f";
                   }else if(data.acceptYN=="N"){
                     data.color="#e36c6c";
                   }else{
                     data.color="#5a5afa";
                   }
              data.start = new Date(data.start);
              data.end = new Date(data.end);
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
                slotEventOverlap   : false,  //슬롯 중복 금지 옵션
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
                start : null,
                end  : null,
                allDay : false,
                tel : '',
                reason: ''
              },
      caleandar : {
        startDate : null,
        endDate : null
      },
      ownList : []
         
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
//         this.search();
        if(view.name == 'month'){
          view.options.selectable = false; //월별 달력은 예약일 선택 금지.
        }else{
          view.options.selectable = true;
        }
          
      },
      save : function(){
        if(this.reservation.tel == ''){
          alert("전화번호를 입력해주세요.");
          return;
        }
        if(this.reservation.reason == ''){
          alert("신청 사유를 입력해주세요.");
          return;
        }
        if(this.reservation.start == ''){
          alert("예약일을 선택해주세요.");
          return;
        }
        if(confirm("신청하시겠습니까?")){
          this.$http.post(`${this.store.getters.restWebPath}/reservation`,this.reservation)
          .then(({data})=>{
            alert(data.msg);
            this.$refs.calendar.$emit('refetch-events')
          });
        }
      },
      deleteReserv : function(id){
        if(confirm("예약을 취소하시겠습니까?")){
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
  .reservation {

  }
</style>
<style scoped src="@/assets/css/custom.css"></style>