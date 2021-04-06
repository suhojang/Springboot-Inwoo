package com.soroweb.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.soroweb.board.dto.AuthorityDto;
import com.soroweb.board.dto.MemberDto;
import com.soroweb.board.dto.SecurityMember;
import com.soroweb.board.repository.AuthorityRepository;
import com.soroweb.board.repository.MemberRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	/*
	@Transactional
	public Long joinUser( MemberDto memberDto ) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		memberDto.setMUserPw( passwordEncoder.encode( memberDto.getMUserPw() ) );
		return memberRepository.save( memberDto ).getMId();
	}
	*/

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("USERNAME : " + username);
		MemberDto searchDto = new MemberDto();
		searchDto.setMbUserId( username );
		MemberDto memberDto = memberRepository.findMemberOneByMbUserId( searchDto );
		log.info("memberdto : " + memberDto);
		if( memberDto != null ) {
			AuthorityDto authorityDto = new AuthorityDto();
			authorityDto.setUsername( memberDto.getMbUserId() );
			memberDto.setAuthorities( makeGrantedAuthority( authorityRepository.findAuthorityNameListByUsername( authorityDto ) ) );
		}
		
		return new SecurityMember( memberDto );
	}
	
	private static List<GrantedAuthority> makeGrantedAuthority( List<AuthorityDto> roles ) {
		List<GrantedAuthority> list = new ArrayList<>();
		roles.forEach( role -> list.add( new SimpleGrantedAuthority( "ROLE_" + role.getAuthorityName() ) ) );
		return list;
	}
}
