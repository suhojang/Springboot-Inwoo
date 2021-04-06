package com.soroweb.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soroweb.board.repository.MemberTestRepository;
import com.soroweb.board.vo.MemberVo;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@Slf4j
public class MemberTestService {
	
	@Autowired
	private MemberTestRepository memberTestRepository;
	
	public List<MemberVo> findAll(){
		List<MemberVo> members	= new ArrayList<>();
		memberTestRepository.findAll().forEach(e -> members.add(e));
		
		return members;
	}
	
	public Optional<MemberVo> findByMbUserId(String mbUserId){
		Optional<MemberVo> member	= memberTestRepository.findByMbUserId(mbUserId);
		return member;
	}
}
