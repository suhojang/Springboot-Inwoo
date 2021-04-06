package com.soroweb.board.dto;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {

	private Long mbId;
	private String mbUserId;
	private String mbUserPw;
	private String mbName;
	private String mbEmail;
	private String mbTel;
	private LocalDateTime mbEnrolDt;
	private int isaccountnonexpired;
	private int isaccountnonlocked;
	private int iscredentialsnonexpired;
	private int isenabled;
	private Collection<? extends GrantedAuthority> authorities;
	
	
	@Builder
	public MemberDto( Long mbId, String mbUserId, String mbUserPw, String mbName, String mbEmail, String mbTel, 
			int isaccountnonexpired, int isaccountnonlocked, int iscredentialsnonexpired, int isenabled, Collection<? extends GrantedAuthority> authorities ) {
		this.mbId = mbId;
		this.mbUserId = mbUserId;
		this.mbUserPw = mbUserPw;
		this.mbName = mbName;
		this.mbEmail = mbEmail;
		this.mbTel = mbTel;
		this.isaccountnonexpired = isaccountnonexpired;
		this.isaccountnonlocked = isaccountnonlocked;
		this.iscredentialsnonexpired = iscredentialsnonexpired;
		this.isenabled = isenabled;
		this.authorities = authorities;
	}
}
