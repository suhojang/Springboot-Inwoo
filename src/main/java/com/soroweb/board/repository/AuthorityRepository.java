package com.soroweb.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soroweb.board.dto.AuthorityDto;

@Mapper
public interface AuthorityRepository {

	List<AuthorityDto> findAuthorityNameListByUsername( AuthorityDto authorityDto );
}
