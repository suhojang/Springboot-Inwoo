package com.soroweb.board.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BoardConfigDto {
	private Long bcIdx;
	private String bcDbname;
	private String bcBoardName;
	private String bcBoardType;
	private int bcPageRows;
	private int bcPageBlock;
	private int bcUseSecret;
	private int bcUseReply;
	private int bcUseNotice;
	private int bcUseCategory;
	private int bcUsePageable;
	private int bcIsUse;
	private int bcIsInquiry;
	private Long pdIdx;
	private String bcSkinName;
	private LocalDateTime bcEnrolDt;
	private LocalDateTime bcModDt;
	
	private SubMenuConfigDto subMenuConfigDto;
	
	@Builder
	public BoardConfigDto( Long bcIdx, String bcDbname, String bcBoardName, String bcBoardType, int bcPageRows, int bcPageBlock, int bcUseSecret, int bcUseReply, int bcUseNotice, int bcUseCategory, int bcUsePageable, int bcIsUse, LocalDateTime bcEnrolDt, LocalDateTime bcModDt, String bcSkinName ) {
		this.bcIdx = bcIdx;
		this.bcDbname = bcDbname;
		this.bcBoardName = bcBoardName;
		this.bcBoardType = bcBoardType;
		this.bcPageRows = bcPageRows;
		this.bcPageBlock = bcPageBlock;
		this.bcUseSecret = bcUseSecret;
		this.bcUseReply = bcUseReply;
		this.bcUseNotice = bcUseNotice;
		this.bcUseCategory = bcUseCategory;
		this.bcUsePageable = bcUsePageable;
		this.bcIsUse = bcIsUse;
		this.bcEnrolDt = bcEnrolDt;
		this.bcModDt = bcModDt;
		this.bcSkinName = bcSkinName;
	}
}
