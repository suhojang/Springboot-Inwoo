package com.soroweb.board.constant;

public enum BcBoardType {
	PRODUCT("product"),
	BOARD("board")
	;
	

	final private String name;
	
	BcBoardType( String name ) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
