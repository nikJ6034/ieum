package com.eum.activity.dto;

import com.eum.common.dto.PageDTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ActivitySearchDTO extends PageDTO {
	private Long id;
	private String title;
}
