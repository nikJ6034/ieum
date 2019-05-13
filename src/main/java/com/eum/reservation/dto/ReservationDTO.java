package com.eum.reservation.dto;

import java.util.Date;

import com.eum.member.dto.MemberDTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReservationDTO {
	private Long id;
	private Date start;
	private Date end;
	private String title;
	private String reason;
	private String acceptYN = "N";
	private String deleteYN = "N";
	private MemberDTO member;
}
