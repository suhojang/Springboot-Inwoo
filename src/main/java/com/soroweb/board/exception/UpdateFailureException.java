package com.soroweb.board.exception;

public class UpdateFailureException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	final private String message;
	
	public UpdateFailureException( String message ) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
