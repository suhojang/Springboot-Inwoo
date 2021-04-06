package com.soroweb.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.soroweb.board.dto.MemberDto;

import lombok.NoArgsConstructor;

@Controller
@NoArgsConstructor
public class MemberController {

	@GetMapping( "/member/signUp" )
	public String signUp() {
		return "member/signUp";
	}
	
	@PostMapping( "/member/signUp" )
	public String signUpProcess( MemberDto memberDto ) {
		return "redirect:/member/signIn";
	}
	
	@GetMapping( "/member/signIn" )
	public String signIn() {
		return "member/signIn";
	}

	@RequestMapping( "/member/signIn/result" )
	public String signInResult( MemberDto memberDto ) {
		return "redirect:/";
	}
	
	@PostMapping( "/member/signOut" )
	public String signOut( MemberDto memberDto ) {
		return "member/signOut";
	}
	
	@RequestMapping( "/member/signOut/result" )
	public String signOutResult( MemberDto memberDto ) {
		return "redirect:/";
	}
	
	@RequestMapping( "/member/invalid" )
	public String memberInvalid() {
		return "member/invalid";
	}
}
