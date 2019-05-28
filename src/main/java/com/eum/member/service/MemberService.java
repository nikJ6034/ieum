package com.eum.member.service;

import com.eum.member.dto.SignupDTO;
import com.eum.member.entity.Member;
import com.eum.member.repository.MemberRepository;
import com.eum.role.entity.Role;
import com.eum.social.dto.SocialDTO;
import com.eum.social.dto.TokenDTO;
import com.eum.social.entity.Social;
import com.eum.social.repository.SocialRepository;
import com.eum.socialLogin.ISocialAuth;
import com.eum.socialLogin.SocialLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MemberService {
	
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	SocialRepository socialRepository;
	
	public Member insertMember(SignupDTO signupDTO) {
		TokenDTO tokenDTO = new TokenDTO();
		tokenDTO.setToken(signupDTO.getToken());
		tokenDTO.setKind(signupDTO.getKind());
		Member member = new Member();


		ISocialAuth create = SocialLogin.create(tokenDTO);
		SocialDTO socialDTO = create.userInfo();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		member.setMemberPassword(passwordEncoder.encode("ieumschool"));
		Role role = new Role();
		role.setId(2L); //일반 회원
		member.setRoles(Arrays.asList(role));
		member.setMemberName(signupDTO.getMemberName());
		member.setMobile(signupDTO.getMobile());
		member.setEmail(signupDTO.getEmail());
		member.setName(socialDTO.getName());
		Member save = memberRepository.save(member);

		Social social = new Social();
		social.setKind(tokenDTO.getKind());
		social.setSocialKey(socialDTO.getSocialKey());
		social.setMember(save);
		socialRepository.save(social);

		return save;
	}
	
	public Long existsMember(String memberName){
		return memberRepository.existsByMemberName(memberName);
	}
}
