package com.eum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.eum.config.interceptor.ApiRoleCheckInterceptor;

@Configuration
public class InterceptoerConfig implements WebMvcConfigurer{

	@Autowired
	ApiRoleCheckInterceptor apiRoleCheckInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(apiRoleCheckInterceptor).addPathPatterns("/api/**");
//		WebMvcConfigurer.super.addInterceptors(registry);
	}

	
	
}
