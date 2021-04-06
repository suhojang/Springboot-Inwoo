package com.soroweb.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.soroweb.board.constant.AttributeName;
import com.soroweb.board.constant.ExceptionType;
import com.soroweb.board.dto.BoardConfigDto;
import com.soroweb.board.exception.NotExistPageException;
import com.soroweb.board.service.PageService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/page")
@Slf4j
public class PageController {
	
	@Autowired
	private PageService pageService;

	@RequestMapping( "/company/inwoo" )
	public String companyInwoo( Model model ) {
		return "company/inwoo";
	}
	
	@RequestMapping( "/company/greeting" )
	public String companyGreeting( Model model ) {
		return "company/greeting";
	}
	
	@RequestMapping( "/company/history" )
	public String companyHistory( Model model ) {
		return "company/history";
	}
	
	@RequestMapping( "/company/location" )
	public String companyLocation( Model model ) {
		return "company/location";
	}
	
	@RequestMapping( "/company/joboffer" )
	public String companyJoboffer( Model model ) {
		String bcDbname = "recruit";
		BoardConfigDto boardConfigDto = BoardConfigDto.builder().build();
		boardConfigDto.setBcDbname( bcDbname );
		log.info( "bcDbname : " + bcDbname );
		model.addAttribute( AttributeName.BOARD_CONFIG.getName(), pageService.findBoardConfigOneByBcDbname( boardConfigDto ) );
		return "company/joboffer";
	}
	
	@RequestMapping( "/worldpartner" )
	public String worldPartner( Model model ) {
		model.addAttribute( "providers", pageService.findWorldPartners() );
		return "worldpartner/worldpartner";
	}
	
	@RequestMapping( "/rnd/institute" )
	public String rndInstitute( Model model ) {
		return "rnd/institute";
	}
	
	@RequestMapping( "/rnd/bio" )
	public String rndBio( Model model ) {
		return "rnd/bio";
	}
	
	@RequestMapping( "/rnd/eco" )
	public String rndEco( Model model ) {
		return "rnd/eco";
	}
	
	@ExceptionHandler( value = NotExistPageException.class )
	public String notExistPage( Model model ) {
		model.addAttribute( AttributeName.EXCEPTION_TYPE.getName(), ExceptionType.NOT_EXIST_PAGE_EXCEPTION.getName() );
		return "error/defaultErrorPage";
	}
	
	@RequestMapping( "/privacy" )
	public String privacy( Model model ) {
		model.addAttribute( AttributeName.SITE_CONFIG.getName(), pageService.findSiteConfig() );
		return "privacy";
	}
	
}

