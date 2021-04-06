package com.soroweb.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soroweb.board.dto.BoardConfigDto;
import com.soroweb.board.dto.ProductCategoryDto;
import com.soroweb.board.dto.ProductDto;
import com.soroweb.board.dto.ProviderDto;
import com.soroweb.board.repository.BoardConfigRepository;
import com.soroweb.board.repository.ProductCategoryRepository;
import com.soroweb.board.repository.ProductRepository;
import com.soroweb.board.repository.ProviderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
	
	@Autowired
	private BoardConfigRepository boardConfigRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	
	@Autowired
	private ProviderRepository providerRepository;
	
	public BoardConfigDto findBoardConfigOneByBcDbname(BoardConfigDto boardConfigDto) {
		return boardConfigRepository.findBoardConfigOneByBcDbname( boardConfigDto );
	}

	public List<ProductDto> findPageProductListByBcDbname(ProductDto productDto) {
		return productRepository.findPageProductListByBcDbname( productDto );
	}


	public List<ProductDto> findAllProductListByBcDbname(ProductDto productDto) {
		return productRepository.findAllProductListByBcDbname( productDto );
	}

	public int findPageProductListByBcDbnameCount( ProductDto productDto ) {
		return productRepository.findPageProductListByBcDbnameCount( productDto );
	}

	public List<ProductCategoryDto> findProductCategoryListByBcDbname(ProductCategoryDto productCategoryDto) {
		return productCategoryRepository.findProductCategoryListByBcDbname( productCategoryDto );
	}

	public List<ProviderDto> findProviderListByBcDbnameAndPcatIdx(ProviderDto providerDto) {
		return providerRepository.findProviderListByBcDbnameAndPcatIdx( providerDto );
	}

	public ProductCategoryDto findOneProductCategoryByBcDbnameAndPcatIdx(ProductCategoryDto productCategoryDto) {
		return productCategoryRepository.findOneProductCategoryByBcDbnameAndPcatIdx( productCategoryDto );
	}

	public ProductDto findProductOne(ProductDto productDto) {
		return productRepository.findProductOne( productDto );
	}
	
	public List<ProductDto> findAllProductListByLang(ProductDto productDto) {
		return productRepository.findAllProductListByPdLang(productDto);
	}
}
