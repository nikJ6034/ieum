package com.eum.dataRoom.dto;

import com.eum.common.dto.PageDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DataRoomSearchDTO extends PageDTO{
	private Long id;
	private String title;
}
