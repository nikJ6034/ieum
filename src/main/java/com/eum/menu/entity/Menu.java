package com.eum.menu.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Menu {

	@Id
	private Long id;
	@Column(unique=true)
	private String url;
	@Column
	private String menuName;
	
	@Column
	private int level;
	
	@Column(length=1)
	private String naviUseYN = "N";
	
	@Column(length=1)
	private String useYN = "N";
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="pid")
	private List<Menu> menuList;
	
	private Long pid;
}
