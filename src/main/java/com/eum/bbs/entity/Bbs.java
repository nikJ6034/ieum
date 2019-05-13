package com.eum.bbs.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.eum.member.entity.Member;

import lombok.Data;

@Entity
@Data
public class Bbs {
	@Id @GeneratedValue
	private Long id;
	
	@Column(nullable=true) 
	private Long pid;
	
	@Column(length=10)
	private String bbsType;

	@Column(nullable=false) 
	private String title;
	@Column(nullable=false) @Lob
	private String content;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="reg_id")
	private Member member;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate = new Date();
	
	@Column 
	private String updateId;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	
	@Column String useYN = "Y";
	
	
}
