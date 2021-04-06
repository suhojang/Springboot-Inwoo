package com.soroweb.board.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.soroweb.board.service.MemberService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MemberService memberService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers( "/static/**" );
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers( "/admin/**" ).hasAnyRole( "ADMIN", "DEVELOPER" )
				.antMatchers( "/board/notice/write", "/board/modify/*", "/board/delete/*" ).hasAnyRole( "ADMIN", "DEVELOPER" )
				.antMatchers( "/**", "/**/*", "/member/signIn" ).permitAll()
				.antMatchers( "/static/**" ).permitAll()
			.and()
				.formLogin()
				.loginPage( "/member/signIn" )
				.usernameParameter( "username" )
				.passwordParameter( "password" )
				.defaultSuccessUrl( "/member/signIn/result" )
				.failureForwardUrl( "/member/invalid" )
				.permitAll()
			.and()
				.logout()
				.logoutRequestMatcher( new AntPathRequestMatcher( "/member/signOut" ) )
				.logoutSuccessUrl( "/member/signOut/result" )
				.invalidateHttpSession( true )
			.and()
				.exceptionHandling().accessDeniedPage( "/error/403" )
			;
		
		http.csrf().and().cors().disable();
		
		// http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true); // 중복로그인 방지
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService( memberService ).passwordEncoder( passwordEncoder() );
	}

}
