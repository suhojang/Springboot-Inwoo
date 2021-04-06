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
public class SubMenuConfigDto {
	private Long smcIdx;
	private Long mcIdx;
	private String smcName;
	private String smcCategory;
	private String smcUrl;
	private int smcOrder=0;
	private int smcIsUse=0;
	private String smcLang;
	
	private BoardConfigDto boardConfigDto;
	
	@Builder
	public SubMenuConfigDto( Long smcIdx, Long mcIdx, String smcName, String smcCategory, String smcUrl, int smcOrder, int smcIsUse, String smcLang ) {
		this.smcIdx = smcIdx;
		this.mcIdx = mcIdx;
		this.smcName = smcName;
		this.smcCategory = smcCategory;
		this.smcUrl = smcUrl;
		this.smcOrder = smcOrder;
		this.smcIsUse = smcIsUse;
		this.smcLang = smcLang;
	}
	
}
