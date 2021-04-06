package com.soroweb.board.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.soroweb.board.dto.BoardDto.BoardDtoBuilder;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductDto {
	private Long pdIdx;
	private String pdName;
	private String pdCategory;
	private String pdType;
	private String pdManufacturer;
	private String pdLang;
	private String pdOrder;
	
	private String pdD1;
	private String pdD2;
	private String pdD3;
	private String pdD4;
	private String pdD5;
	
	private String pdTel;
	
	private String pdDetail;
	
	private LocalDateTime pdEnrolDt;
	private LocalDateTime pdModDt;
	
	private String bcDbname;
	
	private String pdImg1;
	private String pdImg2;
	private String pdImg3;
	private String pdImg4;
	private String pdImg5;
	
	private MultipartFile pdImg1file;
	private MultipartFile pdImg2file;
	private MultipartFile pdImg3file;
	private MultipartFile pdImg4file;
	private MultipartFile pdImg5file;
	
	private Long pcatIdx;
	private int pdIsUse;
	
	private ProductCategoryDto productCategoryDto;
	
	private Pageable pageable;
	
	private int pageSize = 10;
	private int pageNumber = 0;
	
	@Builder
	public ProductDto( Long pdIdx, String pdName, String pdCategory, String pdType, String pdManufacturer, String pdLang, String pdOrder, String pdD1, String pdD2, String pdD3, String pdD4, String pdD5, 
			String pdDetail, String bcDbname, String pdImg1, String pdImg2, String pdImg3, String pdImg4, String pdImg5 ) {
		this.pdIdx = pdIdx;
		this.pdName = pdName;
		this.pdCategory = pdCategory;
		this.pdType = pdType;
		this.pdManufacturer = pdManufacturer;
		this.pdLang = pdLang;
		this.pdOrder = pdOrder;
		this.pdD1 = pdD1;
		this.pdD2 = pdD2;
		this.pdD3 = pdD3;
		this.pdD4 = pdD4;
		this.pdD5 = pdD5;
		this.pdDetail = pdDetail;
		this.bcDbname = bcDbname;
		this.pdImg1 = pdImg1;
		this.pdImg2 = pdImg2;
		this.pdImg3 = pdImg3;
		this.pdImg4 = pdImg4;
		this.pdImg5 = pdImg5;
	}
	
	public void setCustomPageable( Pageable pageable ) {
		PageRequest pageRequest = PageRequest.of( pageable.getPageNumber() , pageable.getPageSize() );
		this.pageSize = pageRequest.getPageSize();
		this.pageNumber = pageRequest.getPageNumber();
	}
}
