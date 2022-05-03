package com.rtdc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Configure implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	}
	
	

}
