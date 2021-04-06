package com.soroweb.board.exception;

public class UnusingBoardAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	final private String message;
	
	public UnusingBoardAccessException( String message ) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
