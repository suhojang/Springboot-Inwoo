package com.soroweb.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AuthorityDto {

	private String username;
	private String authorityName;
	
	@Builder
	public AuthorityDto( String username, String authorityName ) {
		this.username = username;
		this.authorityName = authorityName;
	}
}
