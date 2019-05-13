package com.eum.reservation.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.eum.common.entity.BaseEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.eum.member.entity.Member;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Reservation extends BaseEntity {
	
	@Id @GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date start;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date end;
	
	@Column(nullable=false)
	private String title;
	
	@Column(nullable=false)
	private String tel;
	
	@Column(nullable=false)
	private String reason;
	
	@Column(nullable=false)
	private String acceptYN = "N";
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="reg_id")
	private Member member;

}
