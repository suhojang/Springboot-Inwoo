package com.soroweb.board.repository;

import org.apache.ibatis.annotations.Mapper;

import com.soroweb.board.dto.SiteConfigDto;

@Mapper
public interface SiteConfigRepository {

	SiteConfigDto findOne();

	int update(SiteConfigDto siteConfigDto);

}
