package com.eum.freeBoard.dto;

import com.eum.common.dto.PageDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FreeBoardSearchDTO extends PageDTO{
	private Long id;
	private String title;
}
