package com.soroweb.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import com.soroweb.board.dto.ProductDto;
import com.soroweb.board.dto.ProviderDto;
import com.soroweb.board.service.AjaxService;

@RestController
@RequestMapping("/ajax")
public class AjaxController {
	
	@Autowired
	private AjaxService ajaxService;
	
	@Autowired
	private LocaleResolver localeResolver;

	@RequestMapping( "/getProductList" )
	public List<ProductDto> getProductList( @RequestBody ProductDto productDto ) {
		return ajaxService.findAllProductList( productDto );
	}
	
	@RequestMapping( "/getProductListByLang" )
	public List<ProductDto> getProductListByLang( @RequestBody ProductDto productDto, HttpServletRequest request ) {
		productDto.setPdLang(localeResolver.resolveLocale(request).getLanguage().toLowerCase());
		return ajaxService.findAllProductListByLang( productDto );
	}
	
	@RequestMapping( "/providerList" )
	public List<ProviderDto> providerList( @RequestBody ProviderDto providerDto ) {
		return ajaxService.findAllProviderList( providerDto );
	}
	
	@RequestMapping( "/productListByProvider" )
	public List<ProductDto> productListByProvider( @RequestBody ProviderDto providerDto ) {
		return ajaxService.productListByProvider( providerDto );
	}
}
