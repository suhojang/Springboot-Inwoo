package com.soroweb.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soroweb.board.dto.UploadFileDto;

@Mapper
public interface UploadFileRepository {

	public List<UploadFileDto> findAll();
	public UploadFileDto findOne(UploadFileDto uploadFileDto);
	public Long save(UploadFileDto saveFile);

}
