package com.soroweb.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.soroweb.board.dto.BoardConfigDto;
import com.soroweb.board.dto.MenuConfigDto;
import com.soroweb.board.dto.ProviderDto;
import com.soroweb.board.dto.SiteConfigDto;
import com.soroweb.board.repository.BoardConfigRepository;
import com.soroweb.board.repository.MenuConfigRepository;
import com.soroweb.board.repository.ProviderRepository;
import com.soroweb.board.repository.SiteConfigRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PageService {

	@Autowired
	private MenuConfigRepository menuConfigRepository;
	
	@Autowired
	private BoardConfigRepository boardConfigRepository;
	
	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private SiteConfigRepository siteConfigRepository;

	public MenuConfigDto findPageConfigOneByMcUrl(MenuConfigDto menuConfigDto) {
		// TODO Auto-generated method stub
		return menuConfigRepository.findPageConfigOneByMcUrl( menuConfigDto );
	}

	public BoardConfigDto findBoardConfigOneByBcDbname(BoardConfigDto boardConfigDto) {
		return boardConfigRepository.findBoardConfigOneByBcDbname( boardConfigDto );
	}

	public Map<String, Object> findWorldPartners() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ProviderDto providerDto = ProviderDto.builder().build();
		providerDto.setPvContinent( "ASIA" );
		resultMap.put( "ASIA", providerRepository.findListByBcIsUseAndBcContinent( providerDto ) );
		
		providerDto.setPvContinent( "EUROPE" );
		resultMap.put( "EUROPE", providerRepository.findListByBcIsUseAndBcContinent( providerDto ) );
		
		providerDto.setPvContinent( "AFRICA" );
		resultMap.put( "AFRICA", providerRepository.findListByBcIsUseAndBcContinent( providerDto ) );
		
		providerDto.setPvContinent( "SAMERICA" );
		resultMap.put( "SAMERICA", providerRepository.findListByBcIsUseAndBcContinent( providerDto ) );
		
		providerDto.setPvContinent( "NAMERICA" );
		resultMap.put( "NAMERICA", providerRepository.findListByBcIsUseAndBcContinent( providerDto ) );
		
		providerDto.setPvContinent( "MIDEAST" );
		resultMap.put( "MIDEAST", providerRepository.findListByBcIsUseAndBcContinent( providerDto ) );
		
		providerDto.setPvContinent( "OCEANIA" );
		resultMap.put( "OCEANIA", providerRepository.findListByBcIsUseAndBcContinent( providerDto ) );
		
		return resultMap;
	}

	public SiteConfigDto findSiteConfig() {
		return siteConfigRepository.findOne();
	}

}
