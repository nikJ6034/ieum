package com.eum.notice.dto;

import com.eum.common.dto.PageDTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NoticeSearchDTO extends PageDTO{
	private Long id;
	private String title;
}
