package com.soroweb.board.exception;

public class NotFoundProductCategoryException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	String message;
	
	public NotFoundProductCategoryException( String message ) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}
}
