package com.eum.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.eum.member.entity.Member;
import com.eum.member.repository.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	MemberRepository memberRepository;
	
	public Member insertMember(Member member) {
		
//		MemberRole memberRole = new MemberRole();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		member.setMemberPassword(passwordEncoder.encode(member.getMemberPassword()));
//		memberRole.setRno(1L);
//		memberRole.setRoleName("ADMIN");
//		member.setRoles(Arrays.asList(memberRole));
		Member save = memberRepository.save(member);
		return save;
	}
	
	public Member getMember(Member member) {
		return memberRepository.findByMemberName(member.getMemberName()).get();
	}
}
