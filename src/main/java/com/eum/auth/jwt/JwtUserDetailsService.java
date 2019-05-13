package com.eum.auth.jwt;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.eum.auth.jwt.util.JwtUtil;

@Component
public class JwtUserDetailsService implements UserDetailsService {
	@Override
	public JwtUser loadUserByUsername(String token) {
		DecodedJWT decodedJWT = JwtUtil.tokenToJwt(token);

		if (decodedJWT == null) {
			throw new BadCredentialsException("Not used Token");
		}

		Long id = decodedJWT.getClaim("id").asLong();
		String userName = decodedJWT.getClaim("userName").asString();
		String roles = decodedJWT.getClaim("roles").asString();
		
		return new JwtUser(id, userName, AuthorityUtils.commaSeparatedStringToAuthorityList(roles));
	}
}
