package com.soroweb.board.constant;


public enum AttributeName {
	EXCEPTION_TYPE("EXCEPTION_TYPE"), 
	MEMBER("member"),
	MEMBER_LIST("memberList"),
	MEMBER_COUNT("memberCount"),
	BOARD("board"),
	BOARD_LIST("boardList"),
	BOARD_COUNT("boardCount"),
	BOARD_CONFIG("boardConfig"),
	BOARD_CONFIG_LIST("boardConfigList"),
	BOARD_NOTICE("boardNotice"),
	BOARD_NOTICE_LIST("boardNoticeList"),
	BOARD_RECRUIT_LIST("boardRecruitList"),
	UPLOAD_FILE("uploadFile"),
	UPLOAD_FILE_LIST("uploadFileList"),
	PRODUCT("product"),
	PRODUCT_LIST("productList"),
	PRODUCT_COUNT("productCount"),
	PRODUCT_CATEGORY("productCategory"),
	PRODUCT_CATEGORY_LIST("productCategoryList"),
	PROVIDER("provider"),
	PROVIDER_LIST("providerList"), 
	PAGEABLE("pageable"), ADMIN_BOARD_MENU_LIST("adminBoardMenuList"), ADMIN_PRODUCT_MENU_LIST("adminProductMenuList"), SITE_CONFIG("siteConfig"), BOARD_NOTICE_NAME("boardNoticeName")
	;
	

	final private String name;
	
	AttributeName( String name ) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
