package com.eum.menu.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

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

	@OneToMany(fetch=FetchType.EAGER)
	@Where(clause = "useYN='Y'")
	@JoinColumn(name="pid")
	private List<Menu> menuList;
	
	private Long pid;
}
