package com.eum.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.eum.reservation.dto.ReservationDateDTO;
import com.eum.reservation.entity.QReservation;
import com.eum.reservation.entity.Reservation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;

@Repository
public class ReservationRepositoryDsl extends QuerydslRepositorySupport{

	public ReservationRepositoryDsl() {
		super(Reservation.class);
	}
	
	public List<Reservation> list(ReservationDateDTO reservationDateDTO){
		
		QReservation reservation = QReservation.reservation;
		BooleanBuilder builder = new BooleanBuilder();
		
		builder.and(reservation.start.between(reservationDateDTO.getStartDate(), reservationDateDTO.getEndDate())
				.or(reservation.end.between(reservationDateDTO.getStartDate(), reservationDateDTO.getEndDate())));
		
		
		JPQLQuery<Reservation> list = from(reservation)
				.where(builder, reservation.deleteYN.eq("N") );
		
		return list.fetch();
		
	}
	
	public List<Reservation> listAdmin(ReservationDateDTO reservationDateDTO){
		
		QReservation reservation = QReservation.reservation;
		BooleanBuilder builder = new BooleanBuilder();
		
		builder.and(reservation.start.between(reservationDateDTO.getStartDate(), reservationDateDTO.getEndDate())
				.or(reservation.end.between(reservationDateDTO.getStartDate(), reservationDateDTO.getEndDate())));
		
		
		JPQLQuery<Reservation> list = from(reservation)
				.where(builder);
		
		return list.fetch();
		
	}
	
	public List<Reservation> listByOwn(ReservationDateDTO reservationDateDTO){
		
		QReservation reservation = QReservation.reservation;
		BooleanBuilder builder = new BooleanBuilder();
		
		builder.and(reservation.start.between(reservationDateDTO.getStartDate(), reservationDateDTO.getEndDate())
				.or(reservation.end.between(reservationDateDTO.getStartDate(), reservationDateDTO.getEndDate())));
		
		JPQLQuery<Reservation> list = from(reservation)
				.where(builder, reservation.deleteYN.eq("N"), reservation.member.id.eq(reservationDateDTO.getMemberId()) );
		
		return list.fetch();
		
	}
	
	public long exists(Reservation reservation) {
		QReservation qReservation = QReservation.reservation;
//		BooleanBuilder builder = new BooleanBuilder();
		BooleanExpression or = qReservation.start.gt(reservation.getStart()).and(qReservation.start.lt(reservation.getEnd()))
		.or(qReservation.end.gt(reservation.getStart()).and(qReservation.end.lt(reservation.getEnd())))
		.or(qReservation.start.lt(reservation.getStart()).and(qReservation.end.gt(reservation.getStart())))
		.or(qReservation.start.lt(reservation.getEnd()).and(qReservation.end.gt(reservation.getEnd())))
		.or(qReservation.start.eq(reservation.getStart()).and(qReservation.end.eq(reservation.getEnd())));
		
		return from(qReservation)
				.where(or, qReservation.deleteYN.eq("N") )
				.fetchCount();
		
	}
}
