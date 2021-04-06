package com.soroweb.board.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
public class ThymeLeafConfig {
	private boolean isCache = false;

	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setPrefix( "classpath:templates/" );
		templateResolver.setCharacterEncoding( "UTF-8" );
		templateResolver.setSuffix( ".html" );
		templateResolver.setTemplateMode( "HTML" );
		templateResolver.setCacheable( isCache );
		return templateResolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine( MessageSource messageSource ) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver( templateResolver() );
		templateEngine.addDialect( new LayoutDialect() );
		templateEngine.addDialect( new SpringSecurityDialect() );
		templateEngine.setTemplateEngineMessageSource( messageSource );
		return templateEngine;
	}

	
	@Bean
	@Autowired
	public ViewResolver viewResolver( MessageSource messageSource ) {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine( templateEngine( messageSource ) );
		viewResolver.setCharacterEncoding( "UTF-8" );
		viewResolver.setOrder( 0 );
		return viewResolver;
	}
	
}
