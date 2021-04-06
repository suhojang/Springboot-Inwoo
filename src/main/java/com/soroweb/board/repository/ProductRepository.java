package com.soroweb.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soroweb.board.dto.ProductDto;

@Mapper
public interface ProductRepository {

	public List<ProductDto> findPageProductListByBcDbname(ProductDto productDto);
	public List<ProductDto> findAllProductListByBcDbname(ProductDto productDto);
	public List<ProductDto> findAdminAllProductListByBcDbname(ProductDto productDto);
	public int findPageProductListByBcDbnameCount(ProductDto productDto);
	public ProductDto findProductOne(ProductDto productDto);
	public int deleteByBcDbnameAndPcatIdx(ProductDto productDto);
	public int insert(ProductDto productDto);
	public int update(ProductDto productDto);
	public List<ProductDto> findAllProductList(ProductDto productDto);
	public int deleteByPdIdx(ProductDto productDto);
	public List<ProductDto> findAllProductListByPdLang(ProductDto productDto);

}
