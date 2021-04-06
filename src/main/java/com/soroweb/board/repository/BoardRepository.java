package com.soroweb.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soroweb.board.dto.BoardDto;

@Mapper
public interface BoardRepository {
	public int insertBoardOne(BoardDto boardDto);
	public int deleteBoardOne(BoardDto boardDto);
	public List<BoardDto> findBoardListPageByBcDbname(BoardDto boardDto);
	public int findBoardCountPageByBcDbname(BoardDto boardDto);
	public BoardDto findBoardOneByBoIdx(BoardDto boardDto);
	public int updateBoardOneByBoIdx(BoardDto boardDto);
	public List<BoardDto> findBoardListByBcDbname(BoardDto boardDto);
	public List<BoardDto> findPageBoardListByBcDbname(BoardDto board);
	public int findPageBoardListByBcDbnameCount(BoardDto board);
	public List<BoardDto> findBoardNoticeListByBcDbname(BoardDto board);
	public List<BoardDto> findBoardLatest(BoardDto boardDto);
}
