package com.soroweb.board.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UploadFileDto {

	private Long ufIdx;
	private String ufFileName;
	private String ufSaveFileName;
	private String ufFilePath;
	private String ufContentType;
	private Long ufFileSize;
	private LocalDateTime ufEnrolDt;
	
	@Builder
	public UploadFileDto( Long ufIdx, String ufFileName, String ufSaveFileName, String ufFilePath, String ufContentType, Long ufFileSize, LocalDateTime ufEnrolDt ) {
		this.ufIdx = ufIdx;
		this.ufFileName = ufFileName;
		this.ufSaveFileName = ufSaveFileName;
		this.ufFilePath = ufFilePath;
		this.ufContentType = ufContentType;
		this.ufFileSize = ufFileSize;
		this.ufEnrolDt = ufEnrolDt;
	}
}
