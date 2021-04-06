package com.soroweb.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soroweb.board.dto.ProductCategoryDto;

@Mapper
public interface ProductCategoryRepository {

	public List<ProductCategoryDto> findProductCategoryListByBcDbname(ProductCategoryDto productCategoryDto);
	public ProductCategoryDto findOneProductCategoryByBcDbnameAndPcatIdx(ProductCategoryDto productCategoryDto);
	public int insert(ProductCategoryDto productCategoryDto);
	public int update(ProductCategoryDto productCategory);
	public ProductCategoryDto findOneProductCategoryByPcatIdx(ProductCategoryDto productCategoryDto);
	public int deleteByPcatIdx(ProductCategoryDto productCategory);
	public int deleteByBcDbname(ProductCategoryDto productCategoryDto);

}
