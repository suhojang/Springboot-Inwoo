package com.soroweb.board.dto;

import org.springframework.security.core.userdetails.User;

public class SecurityMember extends User {

	private static final long serialVersionUID = 1L;
	
	private String ip;

	public SecurityMember( MemberDto memberDto ) {
		super( memberDto.getMbUserId(), memberDto.getMbUserPw(), memberDto.getAuthorities() );
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp( String ip ) {
		this.ip = ip;
	}
}
