package com.soroweb.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soroweb.board.dto.BoardFileDto;

@Mapper
public interface BoardFileRepository {

	public BoardFileDto insertBoardFileOne(BoardFileDto boardFileDto);
	public List<BoardFileDto> findFileBoardListByBoIdx(BoardFileDto boardFileDto);
	public int deleteBoardFileListByBoIdx(BoardFileDto boardFileDto);

}
