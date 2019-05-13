package com.eum.bbs.dto;

import java.util.Date;

import com.eum.member.dto.MemberDTO;

import lombok.Data;

@Data
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
	
	private String useYN;
}
