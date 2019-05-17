package com.eum.bbs.dto;

import java.util.Date;

import com.eum.member.dto.MemberDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BbsDTO {
	private Long id;
	
	private Long pid;
	
	private String bbsType;
	
	private String title;
	
	private String content;
	
	private MemberDTO member;
	
	private Date regDate;
	
	private String updateId;
	
	private Date updateDate;
	
	private String deleteYN;
}
