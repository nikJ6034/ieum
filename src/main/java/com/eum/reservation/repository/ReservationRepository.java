package com.eum.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eum.reservation.entity.Reservation;

public interface ReservationRepository  extends JpaRepository<Reservation, Long>{

}
