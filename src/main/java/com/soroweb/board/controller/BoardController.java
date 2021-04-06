package com.soroweb.board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.LocaleResolver;

import com.soroweb.board.constant.AttributeName;
import com.soroweb.board.dto.BoardConfigDto;
import com.soroweb.board.dto.BoardDto;
import com.soroweb.board.dto.MemberDto;
import com.soroweb.board.dto.ProductDto;
import com.soroweb.board.dto.UploadFileDto;
import com.soroweb.board.exception.NotExistBoardDataException;
import com.soroweb.board.exception.NotExistBoardTableException;
import com.soroweb.board.exception.NotMatchUsernameException;
import com.soroweb.board.exception.UnusingBoardAccessException;
import com.soroweb.board.exception.UpdateFailureException;
import com.soroweb.board.service.BoardService;
import com.soroweb.board.service.ProductService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@AllArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private LocaleResolver localeResolver;
	
	@RequestMapping("/{bcDbname}")
	public String listRedirect( Model model, @PathVariable(name="bcDbname") String bcDbname ) {
		return "redirect:/board/" + bcDbname + "/list";
	}
	
	@RequestMapping( "/{bcDbname}/list.ajax" )
	@ResponseBody
	public ResponseEntity<?> listAjax( @PathVariable(name = "bcDbname") String bcDbname, @PageableDefault(size = 15) Pageable pageable, BoardDto boardDto ) {
		Map<String, Object> map = new HashMap<String, Object>();
		BoardConfigDto boardConfigDto = new BoardConfigDto();
		boardConfigDto.setBcDbname( bcDbname );
		BoardConfigDto boardConfig = boardService.findBoardConfigOneByBcDbname( boardConfigDto );
		map.put( AttributeName.BOARD_CONFIG.getName(), boardConfig );
		
		if( Integer.valueOf( boardConfig.getBcUseNotice() ) > 0 ) {
			BoardDto board = BoardDto.builder().build();
			board.setBcDbname( bcDbname );
			map.put( AttributeName.BOARD_NOTICE_LIST.getName(), boardService.findBoardNoticeListByBcDbname( board ) );
		}
		
		if( Integer.valueOf( boardConfig.getBcUsePageable() ) > 0 ) {
			BoardDto board = BoardDto.builder().build();
			board.setBcDbname( bcDbname );
			pageable = PageRequest.of( pageable.getPageNumber(), Integer.valueOf( boardConfig.getBcPageBlock() ) );
			board.setCustomPageable( pageable );
			
			// search filter setting
			if( !StringUtils.isEmpty( boardDto.getFindWord() ) ) {
				if( !StringUtils.isEmpty( boardDto.getFindType() ) ) {
					board.setFindType( boardDto.getFindType() );
				}
				board.setFindWord( boardDto.getFindWord() );
			}
			
			map.put( AttributeName.BOARD_LIST.getName(), boardService.findPageBoardListByBcDbname( board ) );
			map.put( AttributeName.BOARD_COUNT.getName(), boardService.findPageBoardListByBcDbnameCount( board ) );
			Map<String, Object> pageMap = new HashMap<String, Object>();
			int maxPage = (int) Math.ceil( (int)map.get( AttributeName.BOARD_COUNT.getName() ) / pageable.getPageSize() ) - 1;
			int sequenceStart = (int) Math.floor( ( pageable.getPageNumber() ) / pageable.getPageSize() ) * pageable.getPageSize();
			int sequenceEnd = ( sequenceStart + pageable.getPageSize() ) > maxPage ? maxPage : ( sequenceStart + pageable.getPageSize() );
			sequenceEnd = sequenceEnd < 0 ? 0 : sequenceEnd;
			pageMap.put( "maxPage", maxPage );
			pageMap.put( "sequenceStart", sequenceStart );
			pageMap.put( "sequenceEnd", sequenceEnd );
			pageMap.put( "number", pageable.getPageNumber() );
			pageMap.put( "size", pageable.getPageSize() );
			pageMap.put( "prevPage", sequenceStart - 1 < 0 ? 0 : sequenceStart - 1 );
			pageMap.put( "nextPage", sequenceEnd + 1 <= maxPage ? sequenceEnd+1 : maxPage );
			log.info( pageMap.toString() );
			map.put( AttributeName.PAGEABLE.getName(), pageMap );
		} else {
			BoardDto board = BoardDto.builder().build();
			board.setBcDbname( bcDbname );
			
			if( !StringUtils.isEmpty( boardDto.getFindWord() ) ) {
				if( !StringUtils.isEmpty( boardDto.getFindType() ) ) {
					board.setFindType( boardDto.getFindType() );
				}
				board.setFindWord( boardDto.getFindWord() );
			}
			
			map.put( AttributeName.BOARD_LIST.getName(), boardService.findBoardListByBcDbname( board ) );
			map.put( AttributeName.BOARD_COUNT.getName(), boardService.findPageBoardListByBcDbnameCount( board ) );
		}
		
		return ResponseEntity.ok().body( map );
	}
	
	@RequestMapping("/{bcDbname}/list")
	public String list( Model model, @PathVariable(name = "bcDbname") String bcDbname, @PageableDefault(size = 15) Pageable pageable, BoardDto boardDto ) {
		BoardConfigDto boardConfigDto = new BoardConfigDto();
		boardConfigDto.setBcDbname( bcDbname );
		BoardConfigDto boardConfig = boardService.findBoardConfigOneByBcDbname( boardConfigDto );
		model.addAttribute( AttributeName.BOARD_CONFIG.getName(), boardConfig );
		
		if( boardConfig == null ) {
			throw new NotExistBoardTableException( "Not exist board config data" );
		} else if( Integer.valueOf( boardConfig.getBcIsUse() ) == 0 ) {
			throw new UnusingBoardAccessException( "Unusing board access" );
		}
		
		if( Integer.valueOf( boardConfig.getBcUseNotice() ) > 0 ) {
			BoardDto board = BoardDto.builder().build();
			board.setBcDbname( bcDbname );
			model.addAttribute( AttributeName.BOARD_NOTICE_LIST.getName(), boardService.findBoardNoticeListByBcDbname( board ) );
		}
		
		if( Integer.valueOf( boardConfig.getBcUsePageable() ) > 0 ) {
			BoardDto board = BoardDto.builder().build();
			board.setBcDbname( bcDbname );
			pageable = PageRequest.of( pageable.getPageNumber(), Integer.valueOf( boardConfig.getBcPageBlock() ) );
			board.setCustomPageable( pageable );
			
			// search filter setting
			if( !StringUtils.isEmpty( boardDto.getFindWord() ) ) {
				if( !StringUtils.isEmpty( boardDto.getFindType() ) ) {
					board.setFindType( boardDto.getFindType() );
				}
				board.setFindWord( boardDto.getFindWord() );
			}
			
			model.addAttribute( AttributeName.BOARD_LIST.getName(), boardService.findPageBoardListByBcDbname( board ) );
			model.addAttribute( AttributeName.BOARD_COUNT.getName(), boardService.findPageBoardListByBcDbnameCount( board ) );
			Map<String, Object> pageMap = new HashMap<String, Object>();
			int maxPage = (int) Math.ceil( (int)model.getAttribute( AttributeName.BOARD_COUNT.getName() ) / pageable.getPageSize() ) - 1;
			int sequenceStart = (int) Math.floor( ( pageable.getPageNumber() ) / pageable.getPageSize() ) * pageable.getPageSize();
			int sequenceEnd = ( sequenceStart + pageable.getPageSize() ) > maxPage ? maxPage : ( sequenceStart + pageable.getPageSize() );
			sequenceEnd = sequenceEnd < 0 ? 0 : sequenceEnd;
			pageMap.put( "maxPage", maxPage );
			pageMap.put( "sequenceStart", sequenceStart );
			pageMap.put( "sequenceEnd", sequenceEnd );
			pageMap.put( "number", pageable.getPageNumber() );
			pageMap.put( "size", pageable.getPageSize() );
			pageMap.put( "prevPage", sequenceStart - 1 < 0 ? 0 : sequenceStart - 1 );
			pageMap.put( "nextPage", sequenceEnd + 1 <= maxPage ? sequenceEnd+1 : maxPage );
			log.info( pageMap.toString() );
			model.addAttribute( AttributeName.PAGEABLE.getName(), pageMap );
		} else {
			BoardDto board = BoardDto.builder().build();
			board.setBcDbname( bcDbname );
			
			if( !StringUtils.isEmpty( boardDto.getFindWord() ) ) {
				if( !StringUtils.isEmpty( boardDto.getFindType() ) ) {
					board.setFindType( boardDto.getFindType() );
				}
				board.setFindWord( boardDto.getFindWord() );
			}
			
			model.addAttribute( AttributeName.BOARD_LIST.getName(), boardService.findBoardListByBcDbname( board ) );
			model.addAttribute( AttributeName.BOARD_COUNT.getName(), boardService.findPageBoardListByBcDbnameCount( board ) );
		}
		
		
		return "board/skin/" + boardConfig.getBcSkinName() + "/list";
	}
	
	@RequestMapping("/{bcDbname}/write")
	public String write( Model model, @PathVariable(name = "bcDbname") String bcDbname, HttpServletRequest request ) {
		BoardConfigDto boardConfig = BoardConfigDto.builder().build();
		boardConfig.setBcDbname( bcDbname );
		boardConfig = boardService.findBoardConfigOneByBcDbname(boardConfig);
		model.addAttribute( AttributeName.BOARD_CONFIG.getName(), boardConfig );
		
		MemberDto memberDto = MemberDto.builder().build();
		memberDto.setMbUserId( SecurityContextHolder.getContext().getAuthentication().getName() );
		model.addAttribute( AttributeName.MEMBER.getName(), boardService.findMemberOneByMbUserId(memberDto) );
		
		model.addAttribute( AttributeName.SITE_CONFIG.getName(), boardService.findSiteConfig() );
		
		/*
		 *  20.07.08 add by KJH
		 *  문의하기 게시판 > 언어별 관련 상품 리스트 출력
		 */
		if (boardConfig.getBcSkinName().equals("inquiry")) {
			ProductDto productDto = ProductDto.builder().build();
			productDto.setPdLang(localeResolver.resolveLocale(request).getLanguage().toLowerCase());
			model.addAttribute( AttributeName.PRODUCT.getName(), productService.findAllProductListByLang( productDto ) );
		}
		
		return "board/skin/" + boardConfig.getBcSkinName() + "/write";
	}
	
	@RequestMapping( "/write/proc.ajax")
	@ResponseBody
	public ResponseEntity<?> writeProc( Model model, BoardDto boardDto ) throws Exception {
		return ResponseEntity.ok().body( boardService.writeProc( boardDto ) );
	}
	
	@RequestMapping( "/{bcDbname}/view" ) 
	public String view( Model model, @PathVariable(name = "bcDbname") String bcDbname, BoardDto boardDto ) {
		BoardConfigDto boardConfig = BoardConfigDto.builder().build();
		boardConfig.setBcDbname( bcDbname );
		boardConfig = boardService.findBoardConfigOneByBcDbname( boardConfig );
		model.addAttribute( AttributeName.BOARD_CONFIG.getName(), boardConfig );
		
		BoardDto board = boardService.findBoardOneByBoIdx(boardDto);
		model.addAttribute( AttributeName.BOARD.getName(), board);
		log.info( board.toString() );
		List<UploadFileDto> uploadFileList = new ArrayList<UploadFileDto>();
		if( board.getBoFile1() != null ) {
			UploadFileDto uploadFileDto = UploadFileDto.builder().build();
			uploadFileDto.setUfIdx( board.getBoFile1() );
			uploadFileList.add( boardService.findUploadFileOneByUfIdx( uploadFileDto ) );
		}
		if( board.getBoFile2() != null ) {
			UploadFileDto uploadFileDto = UploadFileDto.builder().build();
			uploadFileDto.setUfIdx( board.getBoFile1() );
			uploadFileList.add( boardService.findUploadFileOneByUfIdx( uploadFileDto ) );
		}
		
		if( board != null && board.getPdIdx() != null ) {
			ProductDto productDto = ProductDto.builder().build();
			productDto.setPdIdx( board.getPdIdx() );
			model.addAttribute( AttributeName.PRODUCT.getName(), productService.findProductOne( productDto ) );
		}
		
		
		if( uploadFileList.size() > 0 ) {
			model.addAttribute( AttributeName.UPLOAD_FILE_LIST.getName(), uploadFileList );
		}
		
		return "board/skin/" + boardConfig.getBcSkinName() + "/view";
	}
	
	
	@RequestMapping( "/{bcDbname}/modify")
	public String modify( Model model, BoardDto boardDto, @PathVariable(name = "bcDbname") String bcDbname ) {
		
		BoardConfigDto boardConfigDto = BoardConfigDto.builder().build();
		boardConfigDto.setBcDbname( bcDbname );
		boardConfigDto = boardService.findBoardConfigOneByBcDbname(boardConfigDto);
		model.addAttribute( AttributeName.BOARD_CONFIG.getName(), boardConfigDto );
		
		BoardDto board = boardService.findBoardOneByBoIdx( boardDto );
		if( board != null ) {
			model.addAttribute( AttributeName.BOARD.getName(), board );
			
			MemberDto memberDto = MemberDto.builder().build();
			memberDto.setMbUserId( SecurityContextHolder.getContext().getAuthentication().getName() );
			model.addAttribute( AttributeName.MEMBER.getName(), boardService.findMemberOneByMbUserId( memberDto ) );
		}
		
		return "board/skin/" + boardConfigDto.getBcSkinName() + "/modify";
	}
	
	@RequestMapping( "/modify/proc" )
	public String modifyProc( Model model, BoardDto boardDto, @RequestParam(name = "files") MultipartFile[] files ) {
		BoardDto dtoFromDb = boardService.findBoardOneByBoIdx( boardDto );
		if( dtoFromDb == null ) {
			throw new NotExistBoardDataException( "Not exist board data" );
		} else if( !SecurityContextHolder.getContext().getAuthentication().getName().equals( dtoFromDb.getMbUserId() ) ) {
			throw new NotMatchUsernameException( "Not match user name" );
		} else {
			BoardDto resultBoardDto = boardService.updateBoardOneByBoIdx( boardDto, files );
			if( resultBoardDto == null ) {
				throw new UpdateFailureException( "Update Fail" );
			}
		}
		return "redirect:/board/" + boardDto.getBcDbname() + "/list";
	}
	
	@RequestMapping( "/delete.ajax" )
	@ResponseBody
	public ResponseEntity<?> delete( Model model, @RequestBody BoardDto boardDto ) {
		return ResponseEntity.ok().body( boardService.deleteBoardOne(boardDto) );
	}
}
