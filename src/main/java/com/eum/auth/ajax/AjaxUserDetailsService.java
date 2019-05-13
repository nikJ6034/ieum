package com.eum.auth.ajax;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.eum.auth.jwt.JwtUser;
import com.eum.config.security.CustomGrantedAuthority;
import com.eum.member.entity.Member;
import com.eum.member.repository.MemberRepository;
import com.eum.role.entity.Role;

@Component
public class AjaxUserDetailsService implements UserDetailsService {

	@Autowired
	MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		Member user = memberRepository.findByMemberName(username).orElse(null);

		if (user == null) {
			throw new UsernameNotFoundException(username + "라는 사용자가 없습니다.");
		}

		return new JwtUser(user, makeGrantedAuthority(user.getRoles()));
	}
	
	private static List<GrantedAuthority> makeGrantedAuthority(List<Role> roles){
		List<GrantedAuthority> list = new ArrayList<>();
		roles.forEach(role -> list.add(new CustomGrantedAuthority(role.getId(),role.getRoleName())));
		return list;
	}
}
