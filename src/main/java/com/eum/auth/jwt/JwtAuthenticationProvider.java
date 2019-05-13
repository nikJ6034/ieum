package com.eum.auth.jwt;

import java.security.Principal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.eum.auth.jwt.util.JwtUtil;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = "";
		if (authentication.getCredentials() != null) {
//			throw new BadCredentialsException("Bad token");
			token = authentication.getCredentials().toString();
		}

		if (JwtUtil.verify(token)) {
			
			JwtUser userDetails = jwtUserDetailsService.loadUserByUsername(token);
			return new JwtAuthenticationToken(userDetails.getId(), userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
		} else {
			//토큰이 없거나, 검증되지 안았을 때 익명 유저로 토큰을 대신함.
			Principal principal = ()->"anonymous";
			return new AnonymousAuthenticationToken("anonymous", principal, Arrays.asList(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));
//			throw new BadCredentialsException("Bad token");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
