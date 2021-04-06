package com.soroweb.board.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FileUploadResponse {
	private String fileName;
	private String saveFileName;
	private String contentType;
	private Long size;
}
