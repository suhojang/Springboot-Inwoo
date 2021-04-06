package com.soroweb.board.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuConfigDto {
	private Long mcIdx;
	private String mcName;
	private String mcCategory;
	private String mcUrl;
	private int mcOrder;
	private int mcIsUse;
	private String mcLang;
	
	@Builder
	public MenuConfigDto( Long mcIdx, String mcName, String mcCategory, String mcUrl, int mcOrder, int mcIsUse, String mcLang ) {
		this.mcIdx = mcIdx;
		this.mcName = mcName;
		this.mcCategory = mcCategory;
		this.mcUrl = mcUrl;
		this.mcOrder = mcOrder;
		this.mcIsUse = mcIsUse;
		this.mcLang = mcLang;
	}
	
}
