package com.eum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.eum.config.security.CustomAuthenticationProvider;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
    private CustomAuthenticationProvider authProvider;
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
         return super.authenticationManagerBean();
    }
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
    }
	
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/resources/**").antMatchers(HttpMethod.OPTIONS,"/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
//		.csrf().disable()
//		.formLogin().disable()
//		.logout().disable()
//		.authorizeRequests()
//		.antMatchers("/admin/**").hasRole("ADMIN")
//		.antMatchers("/**").permitAll()
//		.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//		.and()
//		.cors();
		
		.csrf().disable()
		.formLogin().disable()
		.logout().disable()
		.cors().and()
		.authorizeRequests()
		.antMatchers("/oauth/token").permitAll()
		.requestMatchers(CorsUtils::isPreFlightRequest).permitAll().and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		;
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
       configuration.addExposedHeader("Authorization");
       configuration.addExposedHeader("grant_type");
//       configuration.addExposedHeader("ieumAccessToken");
//       configuration.addExposedHeader("userName");
       
       configuration.setAllowCredentials(true);
       configuration.setMaxAge(3600L);
       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       source.registerCorsConfiguration("/**", configuration);
       return source;
   }
	
	@SuppressWarnings("rawtypes")
	@Bean
    public FilterRegistrationBean corsFilterRegistrationBean() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.applyPermitDefaultValues();
//        config.setAllowCredentials(true);
//        config.setAllowedOrigins(Arrays.asList("*"));
//        config.setAllowedHeaders(Arrays.asList("*"));
//        config.setAllowedMethods(Arrays.asList("*"));
//        config.setExposedHeaders(Arrays.asList("content-length"));
//        config.setMaxAge(3600L);
//        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(0);
        return bean;
    }
	
}
