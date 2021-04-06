package com.soroweb.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soroweb.board.dto.BoardConfigDto;

@Mapper
public interface BoardConfigRepository {
	public BoardConfigDto findBoardConfigOneByBcDbname(BoardConfigDto boardConfigDto);
	public List<BoardConfigDto> findBoardConfigListByBcIsUse(BoardConfigDto boardConfigDto);
	public List<BoardConfigDto> findBoardConfigListByBcBoardType(BoardConfigDto boardConfigDto);
	public BoardConfigDto findBoardConfigOneByBcIdx(BoardConfigDto boardConfigDto);
	public int insert(BoardConfigDto insertDto);
	public int update(BoardConfigDto boardConfig);
	public int deleteByBcDbname(BoardConfigDto boardConfigDto);
	public List<BoardConfigDto> findBoardConfigListByBcBoardTypeWithSubMenuConfig(BoardConfigDto boardConfigDto);
}
