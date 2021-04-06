package com.soroweb.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soroweb.board.dto.ProductDto;
import com.soroweb.board.dto.ProviderDto;
import com.soroweb.board.repository.ProductRepository;
import com.soroweb.board.repository.ProviderRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@Slf4j
public class AjaxService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProviderRepository providerRepository;
	
	public List<ProductDto> findAllProductList(ProductDto productDto) {
		return productRepository.findAllProductList(productDto);
	}
	
	public List<ProductDto> findAllProductListByLang(ProductDto productDto) {
		return productRepository.findAllProductListByPdLang(productDto);
	}

	public List<ProductDto> productListByProvider(ProviderDto providerDto) {
		log.info( providerDto.toString() );
		ProviderDto provider = providerRepository.findOne( providerDto );
		ProductDto productDto = ProductDto.builder().build();
		productDto.setBcDbname( provider.getBcDbname() );
		return productRepository.findAllProductListByBcDbname( productDto );
	}

	public List<ProviderDto> findAllProviderList(ProviderDto providerDto) {
		return providerRepository.findAll();
	}

	
}
