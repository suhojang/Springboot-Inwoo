package com.soroweb.board.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soroweb.board.service.MemberTestService;
import com.soroweb.board.vo.MemberVo;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/test")
@Slf4j
public class MemberTestController {
	@Autowired
	private MemberTestService memberTestService;
	
	// 모든 회원 조회
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }) 
	public ResponseEntity<List<MemberVo>> getAllmembers() {
		List<MemberVo> member = memberTestService.findAll(); 
		
		return new ResponseEntity<List<MemberVo>>(member, HttpStatus.OK); 
	}

	// 회원아이디로 한명의 회원 조회
	@GetMapping(value = "/{mbUserId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MemberVo> getMember(@PathVariable("mbUserId") String mbUserId) {
		Optional<MemberVo> member = memberTestService.findByMbUserId(mbUserId);
		
		return new ResponseEntity<MemberVo>(member.get(), HttpStatus.OK); 
	}
}
