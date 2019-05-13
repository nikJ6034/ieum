package com.eum.config.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.eum.member.entity.Member;
import com.eum.member.repository.MemberRepository;
import com.eum.role.entity.Role;
import com.eum.socialLogin.ISocialAuth;
import com.eum.socialLogin.SocialLogin;

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
//	@Autowired 
//	private PasswordEncoder passwordEncoder;
	@Autowired
	MemberRepository memberRepository;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName(); 
		String password = (String) authentication.getCredentials();
		ISocialAuth create = SocialLogin.create("kakao",username);
		UserDetails loadUserByUsername = null;
		try {
			String kakaoKey = create.userInfo().getKakaoKey();
			//회원이 아닌 경우에는 바로 가입하고 로그인 프로세스를 탄다.
			if(!memberRepository.findByKakaoKey(kakaoKey).isPresent()) {
				Member member = new Member();
				member.setMemberName(create.userInfo().getName());
				member.setMemberPassword("카카오 소셜 로그인");
				member.setName(create.userInfo().getName());
				member.setKakaoKey(kakaoKey);
				Role role = new Role();
				role.setId(2L);
				member.setRoles(Arrays.asList(role));
				memberRepository.save(member);
			}
			
			loadUserByUsername = customUserDetailsService.loadUserByUsername(kakaoKey);
			
//			if (!passwordEncoder.matches(password, loadUserByUsername.getPassword())) {
//		      throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
//		    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if(!StringUtils.equals(loadUserByUsername.getUsername(), username)) {
//			throw new BadCredentialsException("존재하지 않는 아이디입니다.");
//		}
		
		return new UsernamePasswordAuthenticationToken(loadUserByUsername, password, loadUserByUsername.getAuthorities());

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
