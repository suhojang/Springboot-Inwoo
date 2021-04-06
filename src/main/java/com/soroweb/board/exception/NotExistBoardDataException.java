package com.soroweb.board.exception;

public class NotExistBoardDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	final private String message;
	
	public NotExistBoardDataException( String message ) {
		// TODO Auto-generated constructor stub
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
