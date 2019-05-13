package com.eum.auth.jwt;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.eum.member.entity.Member;

public class JwtUser extends User {
	private static final long serialVersionUID = 1L;
	private Long id;
	
	public JwtUser(Long id, String userName, List<GrantedAuthority> authorities) {
		super(userName, "", authorities);
		this.id = id;
	}

	public JwtUser(Member member, List<GrantedAuthority> authorities) {
		super(member.getMemberName(), member.getMemberPassword(), authorities);
		this.id = member.getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
