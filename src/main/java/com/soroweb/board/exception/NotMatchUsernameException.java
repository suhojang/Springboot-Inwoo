package com.soroweb.board.exception;

public class NotMatchUsernameException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	final private String message;
	
	public NotMatchUsernameException( String message ) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
