package com.soroweb.board.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.soroweb.board.interceptor.MenuInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	@Qualifier(value = "menuInterceptor" )
	private MenuInterceptor menuInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor( menuInterceptor )
				.excludePathPatterns( "/static/**", "/image/**" )
				.addPathPatterns( "/**" )
				;
	}
	
	@Bean
	public MultipartResolver multipartResolver() {
		StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
		return multipartResolver;
	}
}
