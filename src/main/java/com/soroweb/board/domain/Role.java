package com.soroweb.board.domain;

import lombok.Getter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Getter
public enum Role {
	ADMIN( "ROLE_ADMIN" ),
	MEMBER( "ROLE_MEMBER" );
	
	private String value;
}
