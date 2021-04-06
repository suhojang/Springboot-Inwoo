package com.soroweb.board.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

@Controller
public class LanguageController {
	
	@Autowired
	private LocaleResolver localeResolver;

	@RequestMapping("/lang")
	public String lang( @RequestParam("lang") String lang, HttpServletRequest request, HttpServletResponse response ) {
		Locale locale = new Locale( lang );
		localeResolver.setLocale(request, response, locale);
		return "redirect:/";
	}
}
