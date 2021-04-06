package com.soroweb.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.soroweb.board.property.FileUploadProperties;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties({
	FileUploadProperties.class
})
@PropertySource(value = { 
		"classpath:/application-${spring.profiles.active}.properties" 
})
public class SoroBoardApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources( SoroBoardApplication.class );
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SoroBoardApplication.class, args);
	}
	
	@Bean( name = "uploadPath" )
	public String uploadPath() {
		// return "C:/Workspace"; // for local
		return "/whowantapp/upload";
	}
	

}
