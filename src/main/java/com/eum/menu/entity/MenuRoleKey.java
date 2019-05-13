package com.eum.menu.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.eum.role.entity.Role;

import lombok.Data;

@Data
@Embeddable
public class MenuRoleKey implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="menu_id", referencedColumnName="id")
	private Menu menu;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="role_id", referencedColumnName="id")
	private Role role;

}
