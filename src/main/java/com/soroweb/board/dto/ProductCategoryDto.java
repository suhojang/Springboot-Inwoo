package com.soroweb.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductCategoryDto {
	private Long pcatIdx;
	private String pcatName;
	private String pcatNameEn;
	private Long pdIdx;
	private String bcDbname;
	private int pcatOrder;
	private String pcatDesc;
	private String pcatType;
	private String pcatAddative;
	
	@Builder
	public ProductCategoryDto( Long pcatIdx, String pcatName, String pcatNameEn, Long pdIdx, String bcDbname, int pcatOrder ) {
		this.pcatIdx = pcatIdx;
		this.pcatName = pcatName;
		this.pcatNameEn = pcatNameEn;
		this.pdIdx = pdIdx;
		this.bcDbname = bcDbname;
		this.pcatOrder = pcatOrder;
	}
}
