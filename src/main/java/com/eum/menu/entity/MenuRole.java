package com.eum.menu.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class MenuRole {
	
	@EmbeddedId
	private MenuRoleKey roleMenu;
	
	@Column(length=1)
	private String readRole = "N";
	
	@Column(length=1)
	private String writeRole = "N";
	
	@Column(length=1)
	private String modifyRole = "N";
	
	@Column(length=1)
	private String deleteRole = "N";
}
