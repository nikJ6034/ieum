package com.eum.auth.jwt.util;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eum.auth.jwt.JwtInfo;
import com.eum.auth.jwt.JwtUser;

public class JwtUtil {
	public static String createToken(JwtUser userDetails) {
		return createToken(userDetails, DateUtil.nowAfterDaysToDate(JwtInfo.EXPIRES_LIMIT));
	}
	
	private static String createToken(JwtUser userDetails, Date date) {
		try {
			return JWT.create()
					.withIssuer(JwtInfo.ISSUER)
					.withClaim("id", userDetails.getId())
					.withClaim("userName", userDetails.getUsername())
					.withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
					.withExpiresAt(date)
					.sign(JwtInfo.getAlgorithm());
		} catch (JWTCreationException createEx) {
			return null;
		}
	}
	
	public static Boolean verify(String token) {
		try {
			JWTVerifier verifier = JWT.require(JwtInfo.getAlgorithm()).build();
			verifier.verify(token);
			
			return Boolean.TRUE;
		} catch (JWTVerificationException verifyEx) {
			return Boolean.FALSE;
		}
	}

	public static String refreshToken(JwtUser userDetails) {
		return createToken(userDetails, DateUtil.nowAfterDaysToDate(JwtInfo.EXPIRES_LIMIT));
	}
	
	public static DecodedJWT tokenToJwt(String token) {
		try {
			return JWT.decode(token);
		} catch (JWTDecodeException decodeEx) {
			return null;
		}
	}
}
