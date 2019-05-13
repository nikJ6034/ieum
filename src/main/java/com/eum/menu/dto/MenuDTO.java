package com.eum.menu.dto;

import lombok.Data;

@Data
public class MenuDTO {
	private Long id;
	private String url;
	private String menuName;
	private int level;
	private String naviUseYN = "N";
	private String useYN = "N";
	private Long pid;
}
