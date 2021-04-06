package com.soroweb.board.repository;

import org.apache.ibatis.annotations.Mapper;

import com.soroweb.board.dto.MemberDto;

@Mapper
public interface MemberRepository {
	public MemberDto findMemberOneByMbUserId( MemberDto memberDto );
}
