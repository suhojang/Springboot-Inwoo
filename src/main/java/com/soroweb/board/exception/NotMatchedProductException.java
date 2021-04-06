package com.soroweb.board.exception;

public class NotMatchedProductException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	final private String message;
	
	public NotMatchedProductException( String message ) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
