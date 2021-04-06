package com.soroweb.board.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

import com.soroweb.board.constant.AttributeName;
import com.soroweb.board.dto.BoardDto;
import com.soroweb.board.dto.ProviderDto;
import com.soroweb.board.service.MainService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {

	@Autowired
	private MainService mainService;
	
	@Autowired
	private LocaleResolver localeResolver;
	
	@RequestMapping( value = { "", "/" } )
	public String main( Model model, HttpServletRequest request ) {
		BoardDto boardDto = BoardDto.builder().build();
		
		if( localeResolver.resolveLocale(request).getLanguage().toUpperCase().equals("EN") ) {
			boardDto.setBcDbname( "notice_en" );
		} else {
			boardDto.setBcDbname( "notice" );
		}
		model.addAttribute( AttributeName.BOARD_NOTICE_NAME.getName(), boardDto.getBcDbname() );
		model.addAttribute(AttributeName.BOARD_NOTICE_LIST.getName(), mainService.findNoticeLatest(boardDto));
		if( localeResolver.resolveLocale(request).getLanguage().toUpperCase().equals("EN") ) {
			boardDto.setBcDbname( "recruit_en" );
		} else {
			boardDto.setBcDbname( "recruit" );
		}
		model.addAttribute(AttributeName.BOARD_RECRUIT_LIST.getName(), mainService.findRecruitLatest(boardDto));
		ProviderDto providerDto = ProviderDto.builder().build();
		providerDto.setPvLang( localeResolver.resolveLocale(request).getLanguage() );
		model.addAttribute( AttributeName.PROVIDER_LIST.getName(), mainService.findProviderList( providerDto ) );
		return "index";
	}
}
