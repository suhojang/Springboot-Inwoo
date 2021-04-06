package com.soroweb.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soroweb.board.dto.MenuConfigDto;

@Mapper
public interface MenuConfigRepository {

	public List<MenuConfigDto> findMenuConfigListByMcIsUseOrderByMcOrderAsc( MenuConfigDto menuConfigDto );
	public MenuConfigDto findPageConfigOneByMcUrl(MenuConfigDto menuConfigDto);
	public MenuConfigDto findOneByMcCategoryAndMcUrlLike(MenuConfigDto menuConfigDto);
	public MenuConfigDto findOneByMcIdx(MenuConfigDto menuConfigDto); 
}
