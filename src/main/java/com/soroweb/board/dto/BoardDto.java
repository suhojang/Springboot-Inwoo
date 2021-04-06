package com.soroweb.board.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
	private Long boIdx;
	private String bcDbname;
	private String boDevice;
	private String boCategory;
	private String boTitle;
	private Long boReadCnt;
	private String boContent;
	private String mbUserId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime boEnrolDt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime boModDt;
	private int boVisible;
	private String boPassword;
	private int boIsNotice;
	private Long boFile1;
	private Long boFile2;
	private MultipartFile boFile1file;
	private MultipartFile boFile2file;
	
	private String boD1;
	private String boD2;
	private String boD3;
	private String boD4;
	private String boD5;
	
	private String boTel;
	private String boEmail;
	private Long pdIdx;
	private int boReplyReq;
	
	private Pageable pageable;
	
	private int pageSize = 10;
	private int pageNumber = 0;
	
	private MemberDto memberDto;
	private BoardConfigDto boardConfigDto;
	private ProductDto productDto;
	
	//for searcing parameter
	private LocalDateTime boSearchStartDt;
	private LocalDateTime boSearchEndDt;
	private String boSearchTitle;
	private String boSearchContent;
	private String boOrderField = "bo_enrol_dt";
	private String boOrderType = "DESC";
	
	private String findType;
	private String findWord;
	
	@Builder
	public BoardDto( Long boIdx, String bcDbname, String boDevice, String boCategory, String boTitle, Long boReadCnt, 
			String boContent, String mbUserId, LocalDateTime boEnrolDt, LocalDateTime boModDt, int boVisible ) {
		this.boIdx = boIdx;
		this.bcDbname = bcDbname;
		this.boDevice = boDevice;
		this.boCategory = boCategory;
		this.boTitle = boTitle;
		this.boReadCnt = boReadCnt;
		this.boContent = boContent;
		this.mbUserId = mbUserId;
		this.boEnrolDt = boEnrolDt;
		this.boModDt = boModDt;
		this.boVisible = boVisible;
	}
	
	public void setCustomPageable( Pageable pageable ) {
		PageRequest pageRequest = PageRequest.of( pageable.getPageNumber() , pageable.getPageSize() );
		this.pageSize = pageRequest.getPageSize();
		this.pageNumber = pageRequest.getPageNumber();
	}
}
