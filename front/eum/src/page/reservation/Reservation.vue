<template lang="contactlist-component">

  <section class="reservation">
    <sub-layout>
    	<full-calendar 
    	v-if="store.state.ieumUserId"
    	ref="calendar"
    	:selectable="selectable" 
    	:event-sources="eventSources"
    	:config="config" 
    	@view-render="viewRender" 
    	@event-created="eventSelected"
    	></full-calendar>
    	
    	<div class="row" style="margin:10px;">
    		<div class="col-12">
    			신청 대기  <span style="background:#e36c6c;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
    			신청 완료  <span style="background:#5a5afa;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
    		</div>
    		<div class="col-2">
	    		<div>예약 시작일</div>
    		</div>
    		<div class="col-10">
	    		<span v-if="!reservation.start">위 달력에서 예약하실 시간을 선택해주세요.</span><span>{{reservation.start|formatDateKo}}</span><span v-if="reservation.start">~</span><span>{{reservation.end|formatDateKo}}</span>
    		</div>
    		<div class="col-2">
	    		<div>대관 신청 사유</div>
    		</div>
    		<div class="col-10">
    			<input class="form-control" type="text" v-model="reservation.reason">
   			</div>
    		<div class="col-2">
	    		신청자 연락처
    		</div>
    		<div class="col-10">
	    		<input class="form-control" type="text" v-model="reservation.tel">
    		</div>
    		<div class="col-12"><button @click="save">대관 신청</button></div>
    	</div>
    	
    	<div class="row">
    		<table class="table table-striped table-bordered">
    		<colgroup>
				<col width="30%">
				<col width="40%">
				<col width="15%">
				<col width="15%">
			</colgroup>
   			<thead class="thead-dark text-center">
   				<tr>
    				<td>시간</td>
    				<td>사유</td>
    				<td>상태</td>
    				<td>취소</td>
   				</tr>
   			</thead>
   			<tbody>
    			<tr v-for="reserv in ownList">
    				<td class=text-center>{{reserv.start|formatDateKo}}<br/>{{reserv.end|formatDateKo}}</td>
    				<td>{{reserv.reason}}</td>
    				<td class=text-center>{{(reserv.acceptYN == "N")?"대기":"승인"}}</td>
    				<td class=text-center>
    					<template v-if="reserv.acceptYN == 'N'">
	    					<button type="button" class="btn btn-danger" @click="deleteReserv(reserv.id)">취소</button>
    					</template>
   					</td>
    			</tr>
   			</tbody>
    		</table>
    	</div>
    
    </sub-layout>
  </section>

</template>

<script lang="js">
import 'fullcalendar/dist/fullcalendar.css'
import SubLayout from '@/components/layouts/SubLayout'
import { FullCalendar } from 'vue-full-calendar'
let _this = null;
  export default  {
    name: 'reservation',
    props: [],
    beforeMount() {
    	_this = this;
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
       					_this.contents = data.list;
       					data.list.forEach(function(data){
       						if(data.member.id !=_this.store.state.ieumUserId){
       							data.color="#61db5f";
       						}else if(data.acceptYN=="N"){
       							data.color="#e36c6c";
       						}else{
       							data.color="#5a5afa";
       						}
       					})
       					callback(data.list);
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
    	        	end	: '',
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
    		this.reservation.start = event.start.format();
    		this.reservation.end = event.end.format();
    	},
    	viewRender : function(view, element){
    		this.caleandar.startDate = view.intervalStart.format();
    		this.caleandar.endDate = view.intervalEnd.format();
//     		this.search();
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
