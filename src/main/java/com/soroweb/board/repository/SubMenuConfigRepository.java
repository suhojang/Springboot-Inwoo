package com.soroweb.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soroweb.board.dto.BoardConfigDto;
import com.soroweb.board.dto.SubMenuConfigDto;

@Mapper
public interface SubMenuConfigRepository {

	public List<SubMenuConfigDto> findSubmenuConfigListByMcIdxAndSmcIsUseOrderBySmcOrderAsc(SubMenuConfigDto subMenuConfigDto);

	public SubMenuConfigDto findOneBySmcCategoryAndSmcUrlLike(SubMenuConfigDto currentSubMenu);

	public List<SubMenuConfigDto> findListBySmcCategory(SubMenuConfigDto subMenuConfigDto);

	public SubMenuConfigDto findOneBySmcIdx(SubMenuConfigDto subMenuConfigDto);

	public int insert(SubMenuConfigDto subMenuConfigDto);

	public int update(SubMenuConfigDto updateDto);

	public int deleteMenuConfig(SubMenuConfigDto subMenuConfigDto);

	public List<SubMenuConfigDto> findBoardConfigListByBcBoardTypeWithSubMenuConfig(BoardConfigDto boardConfigDto);

}
