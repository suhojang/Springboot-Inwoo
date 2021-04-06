package com.soroweb.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import com.soroweb.board.dto.BoardDto;
import com.soroweb.board.dto.ProviderDto;
import com.soroweb.board.repository.BoardRepository;
import com.soroweb.board.repository.ProviderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MainService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ProviderRepository providerRepository;
	
	public List<BoardDto> findNoticeLatest( BoardDto boardDto ) {
		return boardRepository.findBoardLatest( boardDto );
	}
	
	public List<BoardDto> findRecruitLatest( BoardDto boardDto ) {
		return boardRepository.findBoardLatest( boardDto );
	}

	public List<ProviderDto> findProviderList( ProviderDto providerDto ) {
		return providerRepository.findAllByPvLang( providerDto );
	}
}
