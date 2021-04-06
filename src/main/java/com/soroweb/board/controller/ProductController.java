package com.soroweb.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.soroweb.board.constant.AttributeName;
import com.soroweb.board.dto.BoardConfigDto;
import com.soroweb.board.dto.ProductCategoryDto;
import com.soroweb.board.dto.ProductDto;
import com.soroweb.board.dto.ProviderDto;
import com.soroweb.board.exception.NotExistBoardTableException;
import com.soroweb.board.exception.NotFoundProductCategoryException;
import com.soroweb.board.exception.NotFoundProductException;
import com.soroweb.board.exception.UnusingBoardAccessException;
import com.soroweb.board.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping( "/product" )
@Slf4j
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/{bcDbname}")
	public String listRedirect( Model model, @PathVariable(name="bcDbname") String bcDbname ) {
		return "redirect:/product/" + bcDbname + "/list";
	}

	@RequestMapping("/{bcDbname}/list")
	public String list( Model model, @PathVariable(name = "bcDbname") String bcDbname, @PageableDefault(size = 15) Pageable pageable, ProductCategoryDto category ) {
		BoardConfigDto boardConfigDto = new BoardConfigDto();
		boardConfigDto.setBcDbname( bcDbname );
		BoardConfigDto boardConfig = productService.findBoardConfigOneByBcDbname( boardConfigDto );
		model.addAttribute( AttributeName.BOARD_CONFIG.getName(), boardConfig );
		
		if( boardConfig == null ) {
			throw new NotExistBoardTableException( "Not exist board config data" );
		} else if( Integer.valueOf( boardConfig.getBcIsUse() ) == 0 ) {
			throw new UnusingBoardAccessException( "Unusing board access" );
		}
		
		ProductDto productDto = ProductDto.builder().build();
		productDto.setBcDbname( bcDbname );
		productDto.setPdIdx( boardConfig.getPdIdx() );
		
		ProductCategoryDto productCategoryDto = ProductCategoryDto.builder().build();
		productCategoryDto.setBcDbname( bcDbname );
		List<ProductCategoryDto> productCategoryList = productService.findProductCategoryListByBcDbname( productCategoryDto );
		if( category.getPcatIdx() != null && category.getPcatIdx() > 0 ) {
			productCategoryDto.setPcatIdx( category.getPcatIdx() );
			productDto.setPcatIdx( category.getPcatIdx() );
		} else {
			productCategoryDto.setPcatIdx( productCategoryList.get(0).getPcatIdx() );
			productDto.setPcatIdx( productCategoryList.get(0).getPcatIdx() );
		}
		model.addAttribute( AttributeName.PRODUCT_CATEGORY.getName(), productService.findOneProductCategoryByBcDbnameAndPcatIdx( productCategoryDto ) );
		model.addAttribute( AttributeName.PRODUCT_CATEGORY_LIST.getName(), productCategoryList );
		
		if( Integer.valueOf( boardConfig.getBcUsePageable() ) > 0 ) {
			pageable = PageRequest.of( pageable.getPageNumber(), Integer.valueOf( boardConfig.getBcPageBlock() ) );
			productDto.setCustomPageable( pageable );
			model.addAttribute( AttributeName.PRODUCT_LIST.getName(), productService.findPageProductListByBcDbname( productDto ) );
			model.addAttribute( AttributeName.PRODUCT_COUNT.getName(), productService.findPageProductListByBcDbnameCount( productDto ) );
			Map<String, Object> pageMap = new HashMap<String, Object>();
			int maxPage = (int) Math.ceil( (int)model.getAttribute( AttributeName.PRODUCT_COUNT.getName() ) / pageable.getPageSize() );
			int sequenceStart = (int) Math.floor( ( pageable.getPageNumber() ) / pageable.getPageSize() ) * pageable.getPageSize();
			int sequenceEnd = ( sequenceStart + pageable.getPageSize() ) > maxPage ? maxPage : ( sequenceStart + pageable.getPageSize() );
			pageMap.put( "maxPage", maxPage );
			pageMap.put( "sequenceStart", sequenceStart );
			pageMap.put( "sequenceEnd", sequenceEnd );
			pageMap.put( "number", pageable.getPageNumber() );
			pageMap.put( "size", pageable.getPageSize() );
			pageMap.put( "prevPage", sequenceStart - 1 < 0 ? 0 : sequenceStart - 1 );
			pageMap.put( "nextPage", sequenceEnd + 1 <= maxPage ? sequenceEnd+1 : maxPage );
			model.addAttribute( AttributeName.PAGEABLE.getName(), pageMap );
		} else {
			model.addAttribute( AttributeName.BOARD_LIST.getName(), productService.findAllProductListByBcDbname( productDto ) );
		}
		
		
		
		
		ProviderDto providerDto = ProviderDto.builder().build();
		providerDto.setBcDbname( bcDbname );
		if( category.getPcatIdx() != null && category.getPcatIdx() > 0 ) {
			providerDto.setPcatIdx( category.getPcatIdx() );
		} else {
			providerDto.setPcatIdx( productCategoryList.size() > 0 ? productCategoryList.get(0).getPcatIdx() : 0 );
		}
		model.addAttribute( AttributeName.PROVIDER_LIST.getName(), productService.findProviderListByBcDbnameAndPcatIdx( providerDto ) );
		
		return "product/skin/" + boardConfig.getBcSkinName() + "/list";
		
	}
	
	@RequestMapping( "/{bcDbname}/view" ) 
	public String productView( Model model, @PathVariable(name = "bcDbname") String bcDbname, ProductDto productDto, ProductCategoryDto productCategoryDto ) {
		BoardConfigDto boardConfigDto = new BoardConfigDto();
		boardConfigDto.setBcDbname( bcDbname );
		BoardConfigDto boardConfig = productService.findBoardConfigOneByBcDbname( boardConfigDto );
		
		// Product Category Info Setting
		if( productCategoryDto.getPcatIdx() != null && productCategoryDto.getPcatIdx() > 0 ) {
			ProductCategoryDto productCategory = productService.findOneProductCategoryByBcDbnameAndPcatIdx( productCategoryDto );
			if( productCategory != null ) {
				model.addAttribute( AttributeName.PRODUCT_CATEGORY.getName(), productCategory );
			} else {
				throw new NotFoundProductCategoryException( "Has not found product category" );
			}
		} else {
			throw new NotFoundProductCategoryException( "Has not found product category" );
		}
		
		// Product Info Setting
		if( productDto.getPdIdx() != null && productDto.getPdIdx() > 0 ) {
			ProductDto product = productService.findProductOne( productDto );
			if( product != null ) {
				model.addAttribute( AttributeName.PRODUCT.getName(), product );
			} else {
				throw new NotFoundProductException( "Has not found product" );
			}
		} else {
			throw new NotFoundProductException( "Has not found product" );
		}
		
		return "product/skin/" + boardConfig.getBcSkinName() + "/view";
	}
}
