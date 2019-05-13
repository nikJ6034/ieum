package com.eum.notice.dto;

import com.eum.bbs.dto.BbsDTO;

import lombok.Data;

@Data
public class NoticeDTO{
	private Long id;
    private BbsDTO bbs;
}
