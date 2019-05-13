package com.eum.auth;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.eum.auth.jwt.JwtInfo;
import com.eum.auth.jwt.JwtUser;
import com.eum.auth.jwt.util.JwtUtil;

@Component
public class BaseSecurityHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler{
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    Authentication authentication) throws IOException {
		JwtUser jwtUser = (JwtUser)authentication.getPrincipal();
		
		JwtUser userDetails = new JwtUser(jwtUser.getId(), jwtUser.getUsername(), new ArrayList<>(authentication.getAuthorities()));
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setHeader(JwtInfo.HEADER_NAME, JwtUtil.createToken(userDetails));
		response.setHeader("userName", authentication.getName());
//		response.setHeader("userId", jwtUser.getId().toString());
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    AuthenticationException exception) {
//		throw new ResponseStatusException(HttpStatus.FORBIDDEN, exception.getMessage());
	}
}
