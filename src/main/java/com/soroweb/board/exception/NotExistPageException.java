package com.soroweb.board.exception;

public class NotExistPageException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	String message;
	
	public NotExistPageException( String message ) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}
}
