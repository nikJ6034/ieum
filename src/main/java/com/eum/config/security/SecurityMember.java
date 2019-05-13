package com.eum.config.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.eum.member.entity.Member;
import com.eum.role.entity.Role;

public class SecurityMember extends User{
	
//	private static final String ROLE_PREFIX = "ROLE_";
	private static final long serialVersionUID = 1L;
	private Long id;
	private String memberName;
	private String name;
	

	public SecurityMember(Member member) {
		super(member.getMemberName(), member.getMemberPassword(), makeGrantedAuthority(member.getRoles()));
		this.id = member.getId();
		this.memberName = member.getMemberName();
		this.name = member.getName();
	}

	private static List<GrantedAuthority> makeGrantedAuthority(List<Role> roles){
		List<GrantedAuthority> list = new ArrayList<>();
		roles.forEach(role -> list.add(new CustomGrantedAuthority(role.getId(),role.getRoleName())));
		return list;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
