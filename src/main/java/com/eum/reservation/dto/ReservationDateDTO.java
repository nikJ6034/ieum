package com.eum.reservation.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReservationDateDTO {
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private Long memberId;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;
	private String acceptYN = "N";
}
