package com.soroweb.board.dto;

import java.time.LocalDateTime;

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
public class ProviderDto {
	
	private Long pvIdx;
	private String pvName;
	private String pvNameEn;
	private String pvLogoImg;
	private String pvDesc;
	private String pvDescEn;
	private String bcDbname;
	private LocalDateTime pvEnrolDt;
	private Long pcatIdx;
	private String pvContinent;
	private int pvIsUse;
	private String pvHomepage;
	private String pvProduct;
	private String pvProductEn;
	private String pvLocation;
	private int pvWorldPartner;
	private String pvLang;
	private String pvAddative;
	private String pvProvide;
	private int pvFooter;
	
	private MultipartFile pvLogoImgFile;
	
	private BoardConfigDto boardConfigDto;
	private ProductCategoryDto productCategoryDto;
	
	@Builder
	public ProviderDto( Long pvIdx, String pvName, String pvLogoImg, String pvDesc, String bcDbname, Long pcatIdx, String pvHomepage) {
		this.pvIdx = pvIdx;
		this.pvName = pvName;
		this.pvLogoImg = pvLogoImg;
		this.pvDesc = pvDesc;
		this.bcDbname = bcDbname;
		this.pcatIdx = pcatIdx;
		this.pvHomepage = pvHomepage;
	}
}
