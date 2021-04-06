package com.soroweb.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soroweb.board.dto.BoardConfigDto;
import com.soroweb.board.dto.ProductCategoryDto;
import com.soroweb.board.dto.ProductDto;
import com.soroweb.board.dto.ProviderDto;
import com.soroweb.board.dto.SiteConfigDto;
import com.soroweb.board.dto.SubMenuConfigDto;
import com.soroweb.board.dto.UploadFileDto;
import com.soroweb.board.repository.BoardConfigRepository;
import com.soroweb.board.repository.ProductCategoryRepository;
import com.soroweb.board.repository.ProductRepository;
import com.soroweb.board.repository.ProviderRepository;
import com.soroweb.board.repository.SiteConfigRepository;
import com.soroweb.board.repository.SubMenuConfigRepository;
import com.soroweb.board.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminService {
	
	@Autowired
	private BoardConfigRepository boardConfigRepository;
	
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private SiteConfigRepository siteConfigRepository;
	
	@Autowired
	private SubMenuConfigRepository subMenuConfigRepository;
	
	@Autowired
	private FileService fileService;
	

	public List<BoardConfigDto> findBoardConfigListByBcBoardType(BoardConfigDto boardConfigDto) {
		return boardConfigRepository.findBoardConfigListByBcBoardType( boardConfigDto );
	}

	public BoardConfigDto findBoardConfigOneByBcDbname(BoardConfigDto boardConfigDto) {
		return boardConfigRepository.findBoardConfigOneByBcDbname( boardConfigDto );
	}

	public Map<String, Object> boardConfigSave(BoardConfigDto boardConfigDto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if( StringUtils.isEmpty( boardConfigDto.getBcBoardName() ) ) {
			resultMap.put( "code", 0 );
			resultMap.put( "msg", "게시판 이름을 입력해주세요." );
		} else if( !StringUtils.isEmpty( boardConfigDto.getBcUsePageable() ) && boardConfigDto.getBcUsePageable() > 0 && StringUtils.isEmpty( boardConfigDto.getBcPageBlock() ) ) {
			resultMap.put( "code",  0 );
			resultMap.put( "msg", "페이지 표시 개수를 입력해주세요." );
		} else if( !StringUtils.isEmpty( boardConfigDto.getBcUsePageable() ) && boardConfigDto.getBcUsePageable() > 0 && StringUtils.isEmpty( boardConfigDto.getBcPageRows() ) ) {
			resultMap.put( "code", 0 );
			resultMap.put( "msg", "페이지당 게시물 수를 입력해주세요." );
		} else if( !StringUtils.isEmpty( boardConfigDto.getBcUsePageable() ) && boardConfigDto.getBcUsePageable() > 0 && Integer.valueOf( boardConfigDto.getBcPageBlock() ) <= 0 || Integer.valueOf( boardConfigDto.getBcPageRows() ) <= 0 ) {
			resultMap.put( "code", 0 );
			resultMap.put( "msg", "허용되지 않는 값입니다." );
		} else if( StringUtils.isEmpty( boardConfigDto.getBcSkinName() ) ) {
			resultMap.put( "code", 0 );
			resultMap.put( "msg", "스킨 설정값은 필수입니다. (기본 : board)" );
		} else if( StringUtils.isEmpty( boardConfigDto.getBcDbname() ) ) {
			resultMap.put( "code", 0 );
			resultMap.put( "msg", "게시판 메타데이터 값이 존재하지 않습니다." );
		} else {
			resultMap.put( "code", 200 );
			resultMap.put( "msg", "정상처리 되었습니다." );
		}
		
		if( resultMap.get( "code" ).toString().equals("200") ) {
			BoardConfigDto boardConfig = boardConfigRepository.findBoardConfigOneByBcIdx( boardConfigDto );
			
			if( boardConfig == null ) {
				BoardConfigDto insertDto = BoardConfigDto.builder().build();
				insertDto.setBcDbname( boardConfigDto.getBcDbname() );
				insertDto.setBcBoardName( boardConfigDto.getBcBoardName() );
				insertDto.setBcBoardType( boardConfigDto.getBcBoardType() );
				if( boardConfigDto.getBcUsePageable() > 0 ) {
					insertDto.setBcPageRows( boardConfigDto.getBcPageRows() );
					insertDto.setBcPageBlock( boardConfigDto.getBcPageBlock() );
				} else {
					insertDto.setBcPageRows( 0 );
					insertDto.setBcPageBlock( 0 );
				}
				
				insertDto.setBcUseSecret( 0 );
				insertDto.setBcUseReply( 0 );
				insertDto.setBcUseNotice( boardConfigDto.getBcUseNotice() );
				insertDto.setBcUseCategory( boardConfigDto.getBcUseCategory() );
				insertDto.setBcUsePageable( boardConfigDto.getBcUsePageable() );
				insertDto.setBcIsUse( boardConfigDto.getBcIsUse() );
				insertDto.setBcSkinName( boardConfigDto.getBcSkinName() );
				
				int result = boardConfigRepository.insert( insertDto );
				if( result <= 0 ) {
					resultMap.put( "code", 0 );
					resultMap.put( "msg", "데이터베이스 오류로 인해 처리되지 않았습니다." );
				}
			} else {
				boardConfig.setBcIdx( boardConfigDto.getBcIdx() );
				boardConfig.setBcDbname( boardConfigDto.getBcDbname() );
				boardConfig.setBcBoardName( boardConfigDto.getBcBoardName() );
				boardConfig.setBcBoardType( boardConfigDto.getBcBoardType() );
				if( boardConfigDto.getBcUsePageable() > 0 ) {
					boardConfig.setBcPageRows( boardConfigDto.getBcPageRows() );
					boardConfig.setBcPageBlock( boardConfigDto.getBcPageBlock() );
				} else {
					boardConfig.setBcPageRows( 0 );
					boardConfig.setBcPageBlock( 0 );
				}
				
				boardConfig.setBcUseSecret( 0 );
				boardConfig.setBcUseReply( 0 );
				boardConfig.setBcUseNotice( boardConfigDto.getBcUseNotice() );
				boardConfig.setBcUseCategory( boardConfigDto.getBcUseCategory() );
				boardConfig.setBcUsePageable( boardConfigDto.getBcUsePageable() );
				boardConfig.setBcIsUse( boardConfigDto.getBcIsUse() );
				boardConfig.setBcSkinName( boardConfigDto.getBcSkinName() );
				
				int result = boardConfigRepository.update( boardConfig );
				if( result <= 0 ) {
					resultMap.put( "code", 0 );
					resultMap.put( "msg", "데이터베이스 오류로 인해 처리되지 않았습니다." );
				}
			}
		}
		
		return resultMap;
	}

	public List<ProductCategoryDto> findProductCategoryListByBcDbname(ProductCategoryDto productCategoryDto) {
		return productCategoryRepository.findProductCategoryListByBcDbname( productCategoryDto );
	}

	public Map<String, Object> productCategorySave(ProductCategoryDto productCategoryDto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if( StringUtils.isEmpty( productCategoryDto.getPcatName() ) ) {
			resultMap.put( "code", 0 );
			resultMap.put( "msg", "카테고리명을 입력해주세요." );
		} else if( productCategoryDto.getPcatOrder() < 0 ) {
			resultMap.put( "code", 0 );
			resultMap.put( "msg", "정렬순서는 0보다 커야합니다." );
		} else {
			int result = 0;
			if( productCategoryDto.getPcatIdx() == null ) {
				result = productCategoryRepository.insert( productCategoryDto );
			} else {
				ProductCategoryDto productCategory = ProductCategoryDto.builder().build();
				productCategory.setPcatIdx( productCategoryDto.getPcatIdx() );
				productCategory.setPcatName( productCategoryDto.getPcatName() );
				productCategory.setPcatNameEn( productCategoryDto.getPcatNameEn() );
				productCategory.setBcDbname( productCategoryDto.getBcDbname() );
				productCategory.setPcatOrder( productCategoryDto.getPcatOrder() );
				productCategory.setPdIdx( productCategoryDto.getPdIdx() );
				productCategory.setPcatDesc( productCategoryDto.getPcatDesc() );
				productCategory.setPcatType( productCategoryDto.getPcatType() );
				productCategory.setPcatAddative( productCategoryDto.getPcatAddative() );
				
				result = productCategoryRepository.update( productCategory );
			}
			
			if( result > 0 ) {
				resultMap.put( "code", 200);
				resultMap.put( "msg", "정상처리 되었습니다." );
			} else {
				resultMap.put( "code", 0 );
				resultMap.put( "msg", "데이터베이스 오류로 인해 처리되지 않았습니다." );
			}
		}
		
		
		return resultMap;
	}

	public Map<String, Object> productCategoryDelete(ProductCategoryDto productCategoryDto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ProductCategoryDto productCategory = productCategoryRepository.findOneProductCategoryByPcatIdx( productCategoryDto );
		if( productCategory == null ) {
			resultMap.put( "code", 0 );
			resultMap.put( "msg", "카테고리 정보가 존재하지 않습니다." );
		} else {
			ProductDto productDto = ProductDto.builder().build();
			productDto.setBcDbname( productCategory.getBcDbname() );
			productDto.setPcatIdx( productCategory.getPcatIdx() );
			
			productRepository.deleteByBcDbnameAndPcatIdx( productDto );
			
			int result = productCategoryRepository.deleteByPcatIdx( productCategory );
			
			if( result > 0 ) {
				resultMap.put( "code", 200);
				resultMap.put( "msg", "정상처리 되었습니다." );
			} else {
				resultMap.put( "code", 0 );
				resultMap.put( "msg", "데이터베이스 오류로 인해 처리되지 않았습니다." );
			}
		}
		
		return resultMap;
	}

	public List<ProductDto> findproductListByBcName(ProductDto productDto) {
		return productRepository.findAdminAllProductListByBcDbname(productDto);
	}

	public Map<String, Object> productSave(ProductDto productDto) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if( productDto.getPdIdx() == null ) {
			// insert
			if( StringUtils.isEmpty( productDto.getPdName() ) ) {
				resultMap.put( "code", 0 );
				resultMap.put( "msg", "상품명을 입력해주세요." );
			} else {
				// file handle
				if( productDto.getPdImg1file() != null && !productDto.getPdImg1file().isEmpty() ) {
					UploadFileDto pdImg1Dto = fileService.store( productDto.getPdImg1file() );
					productDto.setPdImg1( String.valueOf( pdImg1Dto.getUfIdx() ) );
				}
				if( productDto.getPdImg2file() != null && !productDto.getPdImg2file().isEmpty() ) {
					UploadFileDto pdImg2Dto = fileService.store( productDto.getPdImg2file() );
					productDto.setPdImg2( String.valueOf( pdImg2Dto.getUfIdx() ) );
				}
				if( productDto.getPdImg3file() != null && !productDto.getPdImg3file().isEmpty() ) {
					UploadFileDto pdImg3Dto = fileService.store( productDto.getPdImg3file() );
					productDto.setPdImg3( String.valueOf( pdImg3Dto.getUfIdx() ) );
				}
				if( productDto.getPdImg4file() != null && !productDto.getPdImg4file().isEmpty() ) {
					UploadFileDto pdImg4Dto = fileService.store( productDto.getPdImg4file() );
					productDto.setPdImg4( String.valueOf( pdImg4Dto.getUfIdx() ) );
				}
				if( productDto.getPdImg5file() != null && !productDto.getPdImg5file().isEmpty() ) {
					UploadFileDto pdImg5Dto = fileService.store( productDto.getPdImg5file() );
					productDto.setPdImg5( String.valueOf( pdImg5Dto.getUfIdx() ) );
				}
				
				int result = productRepository.insert( productDto );
				if( result > 0 ) {
					resultMap.put( "code", 200 );
					resultMap.put( "msg", "정상처리 되었습니다." );
				} else {
					resultMap.put( "code", 0 );
					resultMap.put( "msg", "데이터베이스 오류로 인해 처리되지 않았습니다." );
				}
			}
		} else {
			// update
			ProductDto product = productRepository.findProductOne( productDto );
			if( product == null ) {
				resultMap.put( "code", 0 );
				resultMap.put( "msg", "상품 정보를 찾을 수 없습니다." );
			} else {
				
				if( !StringUtils.isEmpty( productDto.getPdName() ) ) {
					product.setPdName( productDto.getPdName() );
				}
				
				if( !StringUtils.isEmpty( productDto.getPdCategory() ) ) {
					product.setPdCategory( productDto.getPdCategory() );
				}
				
				if( !StringUtils.isEmpty( productDto.getPdManufacturer() ) ) {
					product.setPdManufacturer( productDto.getPdManufacturer() );
				}
				
				if( !StringUtils.isEmpty( productDto.getPdLang() ) ) {
					product.setPdLang( productDto.getPdLang() );
				}
				
				if( !StringUtils.isEmpty( productDto.getPdOrder() ) ) {
					product.setPdOrder( productDto.getPdOrder() );
				}
				
				if( !StringUtils.isEmpty( productDto.getPdD1() ) ) {
					product.setPdD1( productDto.getPdD1() );
				}
				
				if( !StringUtils.isEmpty( productDto.getPdD2() ) ) {
					product.setPdD2( productDto.getPdD2() );
				}
				
				if( !StringUtils.isEmpty( productDto.getPdTel() ) ) {
					product.setPdTel( productDto.getPdTel() );
				}
				
				if( product.getPdDetail() == null ) {
					product.setPdDetail(StringUtils.EMPTY);
				}
				if( !product.getPdDetail().equals(productDto.getPdDetail() ) ) {
					product.setPdDetail( productDto.getPdDetail() );
				}
				
				if( !StringUtils.isEmpty( productDto.getPcatIdx()) ) {
					product.setPcatIdx( productDto.getPcatIdx() ); 
				}
				
				// file handle
				if( productDto.getPdImg1file() != null && !productDto.getPdImg1file().isEmpty() ) {
					UploadFileDto pdImg1Dto = fileService.store( productDto.getPdImg1file() );
					product.setPdImg1( String.valueOf( pdImg1Dto.getUfIdx() ) );
				}
				if( productDto.getPdImg2file() != null && !productDto.getPdImg2file().isEmpty() ) {
					UploadFileDto pdImg2Dto = fileService.store( productDto.getPdImg2file() );
					product.setPdImg1( String.valueOf( pdImg2Dto.getUfIdx() ) );
				}
				if( productDto.getPdImg3file() != null && !productDto.getPdImg3file().isEmpty() ) {
					UploadFileDto pdImg3Dto = fileService.store( productDto.getPdImg3file() );
					product.setPdImg3( String.valueOf( pdImg3Dto.getUfIdx() ) );
				}
				if( productDto.getPdImg4file() != null && !productDto.getPdImg4file().isEmpty() ) {
					UploadFileDto pdImg4Dto = fileService.store( productDto.getPdImg4file() );
					product.setPdImg4( String.valueOf( pdImg4Dto.getUfIdx() ) );
				}
				if( productDto.getPdImg5file() != null && !productDto.getPdImg5file().isEmpty() ) {
					UploadFileDto pdImg5Dto = fileService.store( productDto.getPdImg5file() );
					product.setPdImg5( String.valueOf( pdImg5Dto.getUfIdx() ) );
				}
				
				product.setPdIsUse( productDto.getPdIsUse() );
				
				int result = productRepository.update( product );
				if( result > 0 ) {
					resultMap.put( "code", 200 );
					resultMap.put( "msg", "정상처리 되었습니다." );
				} else {
					resultMap.put( "code", 0 );
					resultMap.put( "msg", "데이터베이스 오류로 인해 처리되지 않았습니다." );
				}
			}
		}
		
		return resultMap;
	}

	public ProductDto findProduct(ProductDto productDto) {
		return productRepository.findProductOne(productDto);
	}

	public List<ProviderDto> findProviderList() {
		return providerRepository.findAllByAdmin();
	}

	public Map<String, Object> providerSave(ProviderDto providerDto) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if( StringUtils.isEmpty( providerDto.getPvName() ) ) {
			resultMap.put( "code", 0 );
			resultMap.put( "msg", "공급자명을 입력해주세요" );
		} else if( StringUtils.isEmpty( providerDto.getPvDesc() ) ) {
			resultMap.put( "code", 0 );
			resultMap.put( "msg", "세부설명을 입력해주세요." );
		} else {
			if( providerDto.getPvIdx() == null ) {
				// insert
				
				// logo handling
				if( providerDto.getPvLogoImgFile() != null && !providerDto.getPvLogoImgFile().isEmpty() ) {
					UploadFileDto uploadFileDto = fileService.store( providerDto.getPvLogoImgFile() );
					providerDto.setPvLogoImg( String.valueOf( uploadFileDto.getUfIdx() ) );
				}
				
				int result = providerRepository.insert( providerDto );
				
				if( result > 0 ) {
					resultMap.put( "code", 200 );
					resultMap.put( "msg", "정상처리 되었습니다." );
				} else {
					resultMap.put( "code", 0 );
					resultMap.put( "msg", "데이터베이스 오류로 인해 처리되지 않았습니다." );
				}
			} else {
				// update
				
				ProviderDto provider = providerRepository.findOneByAdmin( providerDto );
				if( !StringUtils.isEmpty( providerDto.getPvName() ) ) {
					provider.setPvName( providerDto.getPvName() );
				}
				
				if( !StringUtils.isEmpty( providerDto.getPvNameEn() ) ) {
					provider.setPvNameEn( providerDto.getPvNameEn() );
				}
				
				if( providerDto.getPvLogoImgFile() != null && !providerDto.getPvLogoImgFile().isEmpty() ) {
					UploadFileDto uploadFileDto = fileService.store( providerDto.getPvLogoImgFile() );
					provider.setPvLogoImg( String.valueOf( uploadFileDto.getUfIdx() ) );
				}
				
				provider.setBcDbname( providerDto.getBcDbname() );
				provider.setPcatIdx( providerDto.getPcatIdx() );
				provider.setPvDesc( providerDto.getPvDesc() );
				provider.setPvDescEn( providerDto.getPvDescEn() );
				provider.setPvIsUse( providerDto.getPvIsUse() );
				provider.setPvContinent( providerDto.getPvContinent() );
				provider.setPvHomepage( providerDto.getPvHomepage() );
				provider.setPvLocation( providerDto.getPvLocation() );
				provider.setPvProduct( providerDto.getPvProduct() );
				provider.setPvProductEn( providerDto.getPvProductEn() );
				provider.setPvWorldPartner( providerDto.getPvWorldPartner() );
				provider.setPvLang( providerDto.getPvLang() );
				provider.setPvProvide( providerDto.getPvProvide() );
				
				int result = providerRepository.update( provider );
				
				if( result > 0 ) {
					resultMap.put( "code", 200 );
					resultMap.put( "msg", "정상처리 되었습니다." );
				} else {
					resultMap.put( "code", 0 );
					resultMap.put( "msg", "데이터베이스 오류로 인해 처리되지 않았습니다." );
				}
			}
		}
		
		return resultMap;
	}

	public ProviderDto findProviderByPvIdx(ProviderDto providerDto) {
		return providerRepository.findOneByAdmin(providerDto);
	}

	public Map<String, Object> providerDelete(ProviderDto providerDto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ProviderDto provider = providerRepository.findOneByAdmin( providerDto );
		if( provider == null ) {
			resultMap.put( "code", 0 );
			resultMap.put( "msg", "존재하지 않는 데이터입니다." );
		} else {
			provider.setPvIsUse(0);
			int result = providerRepository.deleteByPvIdx(provider);
			if( result > 0 ) {
				resultMap.put( "code", 200 );
				resultMap.put( "msg", "정상처리 되었습니다." );
			} else {
				resultMap.put( "code", 0 );
				resultMap.put( "msg", "데이터베이스 오류로 인해 처리되지 않았습니다." );
			}
		}
		
		return resultMap;
	}

	public ProductCategoryDto findProductCategoryOne(ProductCategoryDto productCategoryDto) {
		return productCategoryRepository.findOneProductCategoryByPcatIdx(productCategoryDto);
	}

	public SiteConfigDto findSiteConfig() {
		return siteConfigRepository.findOne();
	}

	public Map<String, Object> privacySave(SiteConfigDto siteConfigDto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		int result = siteConfigRepository.update( siteConfigDto );
		if( result > 0 ) {
			resultMap.put( "code", 200 );
			resultMap.put( "msg", "정상처리 되었습니다." );
		} else {
			resultMap.put( "code", 0 );
			resultMap.put( "msg", "데이터베이스 오류로 인해 처리되지 않았습니다." );
		}
		
		return resultMap;
	}

	public Map<String, Object> productDelete(ProductDto productDto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		
		int result = productRepository.deleteByPdIdx( productDto );
		if( result > 0 ) {
			resultMap.put( "code", 200 );
			resultMap.put( "msg", "정상처리 되었습니다." );
		} else {
			resultMap.put( "code", 0 );
			resultMap.put( "msg", "데이터베이스 오류로 인해 처리되지 않았습니다." );
		}
		
		return resultMap;
	}

	public Map<String, Object> smtpConfigSave(SiteConfigDto siteConfigDto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		int result = siteConfigRepository.update( siteConfigDto );
		if( result > 0 ) {
			resultMap.put( "code", 200 );
			resultMap.put( "msg", "정상처리 되었습니다." );
		} else {
			resultMap.put( "code", 0 );
			resultMap.put( "msg", "데이터베이스 오류로 인해 처리되지 않았습니다." );
		}
		
		return resultMap;
	}

	public List<SubMenuConfigDto> subMenuConfigList() {
		SubMenuConfigDto subMenuConfigDto = SubMenuConfigDto.builder().build();
		subMenuConfigDto.setSmcCategory( "product" );
		return subMenuConfigRepository.findListBySmcCategory( subMenuConfigDto );
	}

	public SubMenuConfigDto findOneSubMenuConfig(SubMenuConfigDto subMenuConfigDto) {
		log.info( subMenuConfigDto.toString() );
		return subMenuConfigRepository.findOneBySmcIdx( subMenuConfigDto );
	}

	public Map<String, Object> menuConfigSave(SubMenuConfigDto subMenuConfigDto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		int result = 0;
		
		if( subMenuConfigDto.getSmcIdx() != null ) {
			// update
			SubMenuConfigDto updateDto = subMenuConfigRepository.findOneBySmcIdx( subMenuConfigDto );
			
			if( subMenuConfigDto.getSmcLang().toUpperCase().equals( "KO" ) ) {
				updateDto.setMcIdx( 3L );
			} else {
				updateDto.setMcIdx( 7L );
			}
			
			updateDto.setSmcLang( subMenuConfigDto.getSmcLang() );
			updateDto.setSmcName( subMenuConfigDto.getSmcName() );
			updateDto.setSmcOrder( subMenuConfigDto.getSmcOrder() );
			updateDto.setSmcIsUse( subMenuConfigDto.getSmcIsUse() );
			updateDto.setSmcCategory( subMenuConfigDto.getSmcCategory() );
			
			result = subMenuConfigRepository.update( updateDto );
			
		} else {
			// insert
			if( subMenuConfigDto.getSmcLang().toUpperCase().equals( "KO" ) ) {
				subMenuConfigDto.setMcIdx( 3L );
			} else {
				subMenuConfigDto.setMcIdx( 7L );
			}
			subMenuConfigRepository.insert( subMenuConfigDto );
			
			ProductCategoryDto productCategoryDto = ProductCategoryDto.builder().build();
			productCategoryDto.setBcDbname( subMenuConfigDto.getSmcUrl() );
			productCategoryDto.setPcatName( subMenuConfigDto.getSmcName() );
			productCategoryDto.setPcatNameEn( StringUtils.EMPTY );
			productCategoryDto.setPcatOrder(1);
			productCategoryDto.setPcatDesc( StringUtils.EMPTY );
			productCategoryDto.setPcatType( StringUtils.EMPTY );
			productCategoryDto.setPcatAddative( StringUtils.EMPTY );
			
			productCategoryRepository.insert( productCategoryDto );
			
			BoardConfigDto boardConfigDto = BoardConfigDto.builder().build();
			boardConfigDto.setBcDbname( subMenuConfigDto.getSmcUrl() );
			boardConfigDto.setBcBoardName( subMenuConfigDto.getSmcName() );
			boardConfigDto.setBcBoardType( "product" );
			boardConfigDto.setBcPageRows( 5 );
			boardConfigDto.setBcPageBlock( 15 );
			boardConfigDto.setBcUseSecret( 0 );
			boardConfigDto.setBcUseReply( 0 );
			boardConfigDto.setBcUseNotice( 0 );
			boardConfigDto.setBcUseCategory( 0 );
			boardConfigDto.setBcUsePageable( 1 );
			boardConfigDto.setBcIsUse( 1 );
			boardConfigDto.setBcIsInquiry( 0 );
			boardConfigDto.setBcSkinName( "gallery" );
			
			result = boardConfigRepository.insert( boardConfigDto );
		}
		
		if( result > 0 ) {
			resultMap.put( "code", 200 );
			resultMap.put( "msg", "정상처리 되었습니다." );
		} else {
			resultMap.put( "code", 0 );
			resultMap.put( "msg", "데이터베이스 오류로 인해 처리되지 않았습니다." );
		}
		
		return resultMap;
	}
	
	@SuppressWarnings("unused")
	public Map<String, Object> deleteMenuConfig( SubMenuConfigDto subMenuConfigDto ) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		subMenuConfigDto = subMenuConfigRepository.findOneBySmcIdx( subMenuConfigDto );
		
		BoardConfigDto boardConfigDto = BoardConfigDto.builder().build();
		boardConfigDto.setBcDbname( subMenuConfigDto.getSmcUrl() );
		int bcDeleteResult = boardConfigRepository.deleteByBcDbname( boardConfigDto );
		
		ProductCategoryDto productCategoryDto = ProductCategoryDto.builder().build();
		productCategoryDto.setBcDbname( subMenuConfigDto.getSmcUrl() );
		int pcatDeleteResult = productCategoryRepository.deleteByBcDbname( productCategoryDto );
		
		int result = subMenuConfigRepository.deleteMenuConfig( subMenuConfigDto );
		
		if( result > 0 ) {
			resultMap.put( "code", 200 );
			resultMap.put( "msg", "정상처리 되었습니다." );
		} else {
			resultMap.put( "code", 0 );
			resultMap.put( "msg", "데이터베이스 오류로 인해 처리되지 않았습니다." );
		}
		
		return resultMap;
	}

}
