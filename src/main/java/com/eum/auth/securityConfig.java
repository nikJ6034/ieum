package com.eum.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.eum.auth.ajax.AjaxAuthenticationProvider;
import com.eum.auth.ajax.filter.AjaxAuthenticationFilter;
import com.eum.auth.jwt.JwtAuthenticationProvider;
import com.eum.auth.jwt.filter.JwtAuthenticationFilter;
import com.eum.auth.jwt.matcher.SkipPathRequestMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class securityConfig  extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private JwtAuthenticationProvider jwtProvider;
	
	@Autowired
	private AjaxAuthenticationProvider ajaxProvider;
	
	
	@Autowired
	private BaseSecurityHandler securityHandler;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	private static final String LOGIN_ENTRY_POINT = "/api/web/login";
	private static final String TOKEN_ENTRY_POINT = "/api/web/token";
	private static final String ERROR_ENTRY_POINT = "/error";

	
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/resources/**");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(ajaxProvider)
				.authenticationProvider(jwtProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.addFilterBefore(ajaxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//				.addFilterBefore(jwtAuthenticationFilter(), FilterSecurityInterceptor.class)
				.csrf().disable()
				.cors().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				//.antMatchers(ROOT_ENTRY_POINT).authenticated()
				.antMatchers(TOKEN_ENTRY_POINT).permitAll()
				.antMatchers(LOGIN_ENTRY_POINT).permitAll()
				.antMatchers(ERROR_ENTRY_POINT).permitAll();
	}
	
	@Bean
	public AntPathRequestMatcher antPathRequestMatcher() {
		return new AntPathRequestMatcher(LOGIN_ENTRY_POINT, HttpMethod.POST.name());
	}
	
	@Bean
	public AjaxAuthenticationFilter ajaxAuthenticationFilter() throws Exception {
		AjaxAuthenticationFilter filter = new AjaxAuthenticationFilter(antPathRequestMatcher(), objectMapper);
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(securityHandler);
		filter.setAuthenticationFailureHandler(securityHandler);
		return filter;
	}
	
	@Bean
	public SkipPathRequestMatcher skipPathRequestMatcher() {
		return new SkipPathRequestMatcher(Arrays.asList(LOGIN_ENTRY_POINT, TOKEN_ENTRY_POINT, ERROR_ENTRY_POINT));
	}
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(skipPathRequestMatcher());
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationFailureHandler(securityHandler);
		return filter;
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
       CorsConfiguration configuration = new CorsConfiguration();
       configuration.addAllowedOrigin("*");
       configuration.addAllowedMethod(HttpMethod.OPTIONS);
       configuration.addAllowedMethod(HttpMethod.GET);
       configuration.addAllowedMethod(HttpMethod.POST);
       configuration.addAllowedMethod(HttpMethod.PUT);
       configuration.addAllowedMethod(HttpMethod.DELETE);
       configuration.addAllowedHeader("*");
       configuration.addExposedHeader("ieumAccessToken");
       configuration.addExposedHeader("userName");
//       configuration.addExposedHeader("userId");
       
       configuration.setAllowCredentials(true);
       configuration.setMaxAge(3600L);
       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       source.registerCorsConfiguration("/**", configuration);
       return source;
	 }

}
