package com.soroweb.board.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardFileDto {

	private Long bfIdx;
	private Long boIdx;
	private String bfExt;
	private LocalDateTime bfEnrolDt;
	private int bfDownloadable;
	private String bfFileLocation;
	private String bfFileName;
	private String bfFileOrgName;
	private Long bfFileSize;
	private String bfFileContentType;
	
	@Builder
	public BoardFileDto( Long bfIdx, Long boIdx, String bfExt, LocalDateTime bfEnrolDt, int bfDownloadable, String bfFileLocation, String bfFileName, String bfFileOrgName, Long bfFileSize, String bfFileContentType ) {
		this.bfIdx = bfIdx;
		this.boIdx = boIdx;
		this.bfExt = bfExt;
		this.bfEnrolDt = bfEnrolDt;
		this.bfDownloadable = bfDownloadable;
		this.bfFileLocation = bfFileLocation;
		this.bfFileName = bfFileName;
		this.bfFileOrgName = bfFileOrgName;
		this.bfFileSize = bfFileSize;
		this.bfFileContentType = bfFileContentType;
	}
}
