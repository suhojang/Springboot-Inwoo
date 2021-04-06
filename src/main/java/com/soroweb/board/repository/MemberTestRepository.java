package com.soroweb.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soroweb.board.vo.MemberVo;

@Repository
public interface MemberTestRepository extends JpaRepository<MemberVo, Long> {
	public Optional<MemberVo> findByMbUserId(String userId);
}
