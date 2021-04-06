package com.soroweb.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.soroweb.board.constant.AttributeName;
import com.soroweb.board.constant.BcBoardType;
import com.soroweb.board.dto.BoardConfigDto;
import com.soroweb.board.dto.ProductCategoryDto;
import com.soroweb.board.dto.ProductDto;
import com.soroweb.board.dto.ProviderDto;
import com.soroweb.board.dto.SiteConfigDto;
import com.soroweb.board.dto.SubMenuConfigDto;
import com.soroweb.board.dto.UploadFileDto;
import com.soroweb.board.service.AdminService;
import com.soroweb.board.service.FileService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping( "/admin" )
@Slf4j
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private FileService imageService;

	@RequestMapping( value = { "", "/" } )
	public String main( Model model ) {
		return "admin/index";
	}
	
	@RequestMapping( "/board/list/{bcDbname}" )
	public String boardList( Model model, @PathVariable(name = "bcDbname") String bcDbname ) {
		BoardConfigDto boardConfigDto = BoardConfigDto.builder().build();
		boardConfigDto.setBcDbname( bcDbname );
		
		boardConfigDto = adminService.findBoardConfigOneByBcDbname( boardConfigDto );
		model.addAttribute( AttributeName.BOARD_CONFIG.getName(), boardConfigDto );
		return "admin/board/list";
	}
	
	@RequestMapping( "/board/save" )
	@ResponseBody
	public Map<String, Object> boardConfigSave( @RequestBody BoardConfigDto boardConfigDto ) {
		log.info( boardConfigDto.toString() );
		return adminService.boardConfigSave( boardConfigDto );
	}
	
	@RequestMapping( "/product/list/{bcDbname}" )
	public String productList( Model model, @PathVariable(name = "bcDbname") String bcDbname ) {
		BoardConfigDto boardConfigDto = BoardConfigDto.builder().build();
		boardConfigDto.setBcDbname( bcDbname );
		
		boardConfigDto = adminService.findBoardConfigOneByBcDbname( boardConfigDto );
		model.addAttribute( AttributeName.BOARD_CONFIG.getName(), boardConfigDto );
		
		ProductCategoryDto productCategoryDto = ProductCategoryDto.builder().build();
		productCategoryDto.setBcDbname( bcDbname );
		model.addAttribute( AttributeName.PRODUCT_CATEGORY_LIST.getName(), adminService.findProductCategoryListByBcDbname( productCategoryDto ) );
		
		ProductDto productDto = ProductDto.builder().build();
		productDto.setBcDbname( bcDbname );
		model.addAttribute( AttributeName.PRODUCT_LIST.getName(), adminService.findproductListByBcName( productDto ) );
		
		return "admin/product/list";
	}
	
	@RequestMapping( "/product/category/save" )
	@ResponseBody
	public Map<String, Object> productCategorySave( @RequestBody ProductCategoryDto productCategoryDto ) {
		log.info( productCategoryDto.toString() );
		return adminService.productCategorySave( productCategoryDto );
	}
	
	@RequestMapping( "/product/category/modify" )
	public String productCategoryModify( ProductCategoryDto productCategoryDto, Model model ) {
		model.addAttribute( "data", adminService.findProductCategoryOne( productCategoryDto ) );
		return "admin/category/form";
	}
	
	@RequestMapping( "/product/category/delete" )
	@ResponseBody
	public Map<String, Object> productCategoryDelete( @RequestBody ProductCategoryDto productCategoryDto ) {
		return adminService.productCategoryDelete( productCategoryDto );
	}
	
	@RequestMapping( "/product/{bcDbname}/add" )
	public String productAdd( Model model, @PathVariable(name = "bcDbname") String bcDbname ) {
		BoardConfigDto boardConfigDto = BoardConfigDto.builder().build();
		boardConfigDto.setBcDbname( bcDbname );
		model.addAttribute( AttributeName.BOARD_CONFIG.getName(), boardConfigDto );
		
		ProductCategoryDto productCategoryDto = ProductCategoryDto.builder().build();
		productCategoryDto.setBcDbname(bcDbname);
		model.addAttribute( AttributeName.PRODUCT_CATEGORY_LIST.getName(), adminService.findProductCategoryListByBcDbname( productCategoryDto ) );
		return "admin/product/form";
	}
	
	@RequestMapping( "/product/modify" )
	public String productModify( Model model, ProductDto productDto ) {
		ProductDto product = adminService.findProduct( productDto );
	
		BoardConfigDto boardConfigDto = BoardConfigDto.builder().build();
		boardConfigDto.setBcDbname( product.getBcDbname() );
		model.addAttribute( AttributeName.BOARD_CONFIG.getName(), boardConfigDto );
		
		ProductCategoryDto productCategoryDto = ProductCategoryDto.builder().build();
		productCategoryDto.setBcDbname( product.getBcDbname() );
		model.addAttribute( AttributeName.PRODUCT_CATEGORY_LIST.getName(), adminService.findProductCategoryListByBcDbname( productCategoryDto ) );
		
		model.addAttribute( AttributeName.PRODUCT.getName(), adminService.findProduct( productDto ) );
		
		return "admin/product/form";
	}
	
	@RequestMapping( "/product/image" )
	@ResponseBody
	public ResponseEntity<?> handleFileUpload( @RequestParam( "file" ) MultipartFile file ) {
		try {
			UploadFileDto uploadFile = imageService.store( file );
			return ResponseEntity.ok().body( "/image/" + uploadFile.getUfIdx() );
		} catch ( Exception e ) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
	
	@RequestMapping( "/product/save" )
	@ResponseBody
	public ResponseEntity<?> productSave( ProductDto productDto ) throws Exception {
		return ResponseEntity.ok().body( adminService.productSave( productDto ) );
	}
	
	@RequestMapping("/product/delete")
	@ResponseBody
	public ResponseEntity<?> productDelete( @RequestBody ProductDto productDto ) {
		return ResponseEntity.ok().body( adminService.productDelete( productDto ) );
	}
	
	@RequestMapping( "/provider/list" )
	public String providerList( Model model ) {
		model.addAttribute( AttributeName.PROVIDER_LIST.getName(), adminService.findProviderList() );
		return "admin/page/provider_list";
	}
	
	@RequestMapping( "/provider/add" )
	public String providerAdd( Model model ) {
		BoardConfigDto boardConfigDto = BoardConfigDto.builder().build();
		boardConfigDto.setBcBoardType( BcBoardType.PRODUCT.getName() );
		model.addAttribute( AttributeName.BOARD_CONFIG_LIST.getName(), adminService.findBoardConfigListByBcBoardType(boardConfigDto));
		return "admin/page/provider_form";
	}
	
	@RequestMapping( "/provider/modify" )
	public String providerModify( Model model, ProviderDto providerDto ) {
		ProviderDto provider = adminService.findProviderByPvIdx( providerDto );
		if( provider != null ) {
			BoardConfigDto boardConfigDto = BoardConfigDto.builder().build();
			boardConfigDto.setBcBoardType( BcBoardType.PRODUCT.getName() );
			model.addAttribute( AttributeName.BOARD_CONFIG_LIST.getName(), adminService.findBoardConfigListByBcBoardType( boardConfigDto ) );
			
			ProductCategoryDto productCategoryDto = ProductCategoryDto.builder().build();
			productCategoryDto.setBcDbname( provider.getBcDbname() );
			model.addAttribute( AttributeName.PRODUCT_CATEGORY_LIST.getName(), adminService.findProductCategoryListByBcDbname( productCategoryDto ) );
			
			model.addAttribute( AttributeName.PROVIDER.getName(), provider );
		}
		return "admin/page/provider_form";
	}
	
	@RequestMapping( "/provider/productCategoryList" )
	@ResponseBody
	public List<ProductCategoryDto> providerProductCategoryList( @RequestBody ProductCategoryDto productCategoryDto ) {
		return adminService.findProductCategoryListByBcDbname(productCategoryDto);
	}
	
	@RequestMapping( "/provider/save" )
	@ResponseBody
	public ResponseEntity<?> providerSave( ProviderDto providerDto ) throws Exception {
		return ResponseEntity.ok( adminService.providerSave( providerDto ) );
		/*
		Map<String, Object> resultMap = adminService.providerSave( providerDto );
		if( resultMap.get("code").toString().equals("200") ) {
			return ResponseEntity.ok().body( resultMap );
		} else {
			return ResponseEntity.ok().body( resultMap );
		}
		*/
	}
	
	@RequestMapping( "/provider/delete" )
	@ResponseBody
	public ResponseEntity<?> providerDelete ( ProviderDto providerDto ) {
		return ResponseEntity.ok( adminService.providerDelete( providerDto ) );
	}
	
	@RequestMapping( "/privacy" )
	public String privacy( Model model ) {
		model.addAttribute( "siteConfig", adminService.findSiteConfig() );
		return "admin/privacy";
	}
	
	@RequestMapping( "/privacy/save" )
	@ResponseBody
	public ResponseEntity<?> privacySave( SiteConfigDto siteConfigDto ) {
		return ResponseEntity.ok( adminService.privacySave( siteConfigDto ) );
	}
	
	@RequestMapping( "/smtpConfig" )
	public String smtpConfig( Model model ) {
		model.addAttribute( "siteConfig", adminService.findSiteConfig() );
		return "admin/smtpConfig";
	}
	
	@RequestMapping( "/smtpConfig/save" )
	@ResponseBody
	public ResponseEntity<?> smtpConfigSave( SiteConfigDto siteConfigDto ) {
		return ResponseEntity.ok( adminService.smtpConfigSave( siteConfigDto ) );
	}
	
	@RequestMapping( "/menuConfig" )
	public String menuConfig( Model model ) {
		model.addAttribute( "subMenuConfigList", adminService.subMenuConfigList() );
		return "admin/menuConfig";
	}
	
	@RequestMapping( "/menuConfig/insert" )
	public String menuConfigInsert( Model model ) {
		return "admin/menuConfigForm";
	}
	
	@RequestMapping( "/menuConfig/modify/{smcIdx}" )
	public String menuConfigModify( @PathVariable(name = "smcIdx") Long smcIdx, Model model ) {
		SubMenuConfigDto subMenuConfigDto = SubMenuConfigDto.builder().build();
		subMenuConfigDto.setSmcIdx(smcIdx);
		model.addAttribute( "subMenuConfig", adminService.findOneSubMenuConfig( subMenuConfigDto ) );
		return "admin/menuConfigForm";
	}
	
	@RequestMapping( "/menuConfig/save" )
	@ResponseBody
	public ResponseEntity<?> menuConfigSave( SubMenuConfigDto subMenuConfigDto ) {
		return ResponseEntity.ok( adminService.menuConfigSave( subMenuConfigDto ) );
	}
	
	@RequestMapping( "/menuConfig/delete.ajax" )
	@ResponseBody
	public ResponseEntity<?> menuConfigDelete( SubMenuConfigDto subMenuConfigDto ) {
		return ResponseEntity.ok( adminService.deleteMenuConfig( subMenuConfigDto ) );
	}
}
