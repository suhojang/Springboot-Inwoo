package com.soroweb.board.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.soroweb.board.dto.BoardConfigDto;
import com.soroweb.board.dto.BoardDto;
import com.soroweb.board.dto.BoardFileDto;
import com.soroweb.board.dto.MemberDto;
import com.soroweb.board.dto.SiteConfigDto;
import com.soroweb.board.dto.UploadFileDto;
import com.soroweb.board.repository.BoardConfigRepository;
import com.soroweb.board.repository.BoardFileRepository;
import com.soroweb.board.repository.BoardRepository;
import com.soroweb.board.repository.MemberRepository;
import com.soroweb.board.repository.ProductCategoryRepository;
import com.soroweb.board.repository.SiteConfigRepository;
import com.soroweb.board.repository.UploadFileRepository;
import com.soroweb.board.util.SmtpUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class BoardService {
	
	@Autowired
	private SiteConfigRepository siteConfigRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private BoardConfigRepository boardConfigRepository;
	
	@Autowired
	private BoardFileRepository boardFileRepository;
	
	@Autowired
	private UploadFileRepository uploadFileRepository;
	
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public List<BoardConfigDto> findBoardConfigListByBcIsUse( BoardConfigDto boardConfigDto ) {
		return boardConfigRepository.findBoardConfigListByBcIsUse( boardConfigDto );
	}
	
	public BoardConfigDto findBoardConfigOneByBcDbname( BoardConfigDto boardConfigDto ) {
		return boardConfigRepository.findBoardConfigOneByBcDbname( boardConfigDto );
	}
	
	public BoardDto findBoardOneByBoIdx( BoardDto boardDto ) {
		return boardRepository.findBoardOneByBoIdx( boardDto );
	}

	@Transactional
	public BoardDto insertBoard(BoardDto boardDto) {
		boardDto.setMbUserId( SecurityContextHolder.getContext().getAuthentication().getName() );
		boardRepository.insertBoardOne( boardDto );
		return boardDto;
	}
	
	public BoardDto findOneBoardByBcDbnameAndBoIdx( BoardDto boardDto ) {
		return boardRepository.findBoardOneByBoIdx( boardDto );
	}

	public Map<String, Object> findBoardPageListByBcDbname(BoardDto boardDto) {
		Map<String, Object> boardMap = new HashMap<String, Object>();
		BoardConfigDto boardConfigDto = new BoardConfigDto();
		boardConfigDto.setBcDbname( boardDto.getBcDbname() );
		BoardConfigDto boardConfig = boardConfigRepository.findBoardConfigOneByBcDbname( boardConfigDto );
		boardDto.setPageSize( Integer.valueOf( boardConfig.getBcPageRows() ) );
		boardMap.put("list", boardRepository.findBoardListPageByBcDbname( boardDto ) );
		boardMap.put("totalCount", boardRepository.findBoardCountPageByBcDbname( boardDto ) );
		return boardMap;
	}

	public Map<String, Object> deleteBoardOne(BoardDto boardDto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if( boardRepository.findBoardOneByBoIdx( boardDto ) == null ) {
			resultMap.put( "code", 0 );
			resultMap.put( "msg", "게시글이 존재하지 않습니다." );
		} else {
			int result = boardRepository.deleteBoardOne( boardDto ); 
			
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

	public MemberDto findMemberOneByMbUserId(MemberDto memberDto) {
		return memberRepository.findMemberOneByMbUserId( memberDto );
	}

	public void boardFileUpload(BoardDto boardDto, MultipartFile[] files) {
		String uploadPath = "/upload/" + boardDto.getBcDbname() + "/";
		log.info( "FILES : " + files.toString() );
		for( int i=0; i<files.length; i++ ) {
			
			boolean isSaved = false;
			
			String sourceFileName = files[i].getOriginalFilename();
			String sourceFileNameExtension = FilenameUtils.getExtension( sourceFileName ).toLowerCase();
			
			log.info( "SOURCEFILENAME : " + sourceFileName );
			log.info( "SOURCEFILENAMEEXTENSION : " + sourceFileNameExtension );
			
			File destinationFile;
			String destinationFileName;
			
			do {
				destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
				destinationFile = new File( uploadPath + destinationFileName );
			} while( destinationFile.exists() );
			
			destinationFile.getParentFile().mkdirs();
			try {
				files[i].transferTo( destinationFile );
				isSaved = true;
			} catch (IllegalStateException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			log.info( "ISSAVED : " + isSaved);
			
			if( isSaved ) {
				BoardFileDto boardFileDto = new BoardFileDto();
				
				boardFileDto.setBoIdx( boardDto.getBoIdx() );
				boardFileDto.setBfDownloadable(1);
				boardFileDto.setBfExt( sourceFileNameExtension );
				boardFileDto.setBfFileLocation( uploadPath );
				boardFileDto.setBfFileName( destinationFileName );
				boardFileDto.setBfFileOrgName( sourceFileName );
				boardFileDto.setBfFileSize( files[i].getSize() );
				boardFileDto.setBfFileContentType( files[i].getContentType() );
				
				boardFileRepository.insertBoardFileOne( boardFileDto );
			}
		}
	}

	public BoardDto updateBoardOneByBoIdx( BoardDto board, MultipartFile[] files ) {
		// TODO Auto-generated method stub
		BoardDto boardDto = boardRepository.findBoardOneByBoIdx( board );
		boardDto.setBcDbname( board.getBcDbname() );
		boardDto.setBoCategory( board.getBoCategory() );
		boardDto.setBoContent( board.getBoContent() );
		boardDto.setBoDevice( board.getBoDevice() );
		boardDto.setBoEnrolDt( board.getBoEnrolDt() );
		boardDto.setBoIdx( board.getBoIdx() );
		boardDto.setBoVisible( board.getBoVisible() );
		boardDto.setBoModDt( LocalDateTime.now() );
		boardDto.setBoPassword( board.getBoPassword() );
		boardDto.setBoTitle( board.getBoTitle() );
		boardDto.setMbUserId( board.getMbUserId() );
		
		// 등록되었던 파일을 전체 삭제
		BoardFileDto boardFileDto = new BoardFileDto();
		boardFileDto.setBoIdx( board.getBoIdx() );
		boardFileRepository.deleteBoardFileListByBoIdx( boardFileDto );
		
		// 업로드 한 파일을 다시 등록
		boardFileUpload( boardDto, files );
		
		boardRepository.updateBoardOneByBoIdx( boardDto );
		
		return boardDto;
	}

	public List<BoardDto> findBoardListByBcDbname(BoardDto boardDto) {
		return boardRepository.findBoardListByBcDbname( boardDto );
	}

	public List<BoardDto> findPageBoardListByBcDbname(BoardDto board) {
		log.info( board.toString() );
		return boardRepository.findPageBoardListByBcDbname( board );
	}

	public int findPageBoardListByBcDbnameCount(BoardDto board) {
		return boardRepository.findPageBoardListByBcDbnameCount( board );
	}

	public List<BoardDto> findBoardNoticeListByBcDbname(BoardDto board) {
		return boardRepository.findBoardNoticeListByBcDbname( board );
	}

	public Map<String, Object> writeProc(BoardDto boardDto) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if( boardDto.getBoIdx() == null ) {
		
			if( StringUtils.isEmpty( boardDto.getBoTitle() ) ) {
				resultMap.put( "code", 0 );
				resultMap.put( "msg", "제목을 입력해주세요." );
			} else {
				if( StringUtils.isEmpty( boardDto.getBoContent() ) ) {
					boardDto.setBoContent("내용을 등록하지 않았습니다.");
				}
				// file handling
				if( boardDto.getBoFile1file() != null && !boardDto.getBoFile1file().isEmpty() ) {
					UploadFileDto uploadFileDto = fileService.store( boardDto.getBoFile1file() );
					boardDto.setBoFile1( uploadFileDto.getUfIdx() );
				}
				
				// member data handling
				boardDto.setMbUserId( SecurityContextHolder.getContext().getAuthentication().getName() );
				
				log.info( boardDto.toString() );
				
				int result = boardRepository.insertBoardOne( boardDto );
				
				if( result > 0 ) {
					SmtpUtils smtpUtils = new SmtpUtils( javaMailSender );
					
					SiteConfigDto siteConfigDto = siteConfigRepository.findOne();
					String fromName = "인우코퍼레이션";
					String type = "";
					
					smtpUtils.setFrom( "inwoo@inwoocorp.co.kr", fromName);
					
					if( boardDto.getBcDbname().toUpperCase().equals("INQUIRY") ) {
						smtpUtils.setTo( siteConfigDto.getScSmtpInquiry() );
						type = "문의";
					} else if( boardDto.getBcDbname().toUpperCase().equals( "ESTIMATE" ) ) {
						smtpUtils.setTo( siteConfigDto.getScSmtpEstimate() );
						type = "견적문의";
					} else if( boardDto.getBcDbname().toUpperCase().equals( "SAMPLE" ) ) {
						smtpUtils.setTo( siteConfigDto.getScSmtpSample() );
						type = "샘플요청";
					}
					
					smtpUtils.setSubject( "문의 요청이 등록되었습니다. " + type );
					String text = ""
							+ "<table>"
								+ "<thead>"
									+ "<tr>"
										+ "<th colspan='2'>"
											+ "문의 요청이 등록되었습니다.<br/>" + "http://www.inwoocorp.co.kr/board/" + boardDto.getBcDbname() + "/view?boIdx=" + boardDto.getBoIdx()
										+ "</th>"
									+ "</tr>"
								+ "</thead>"
								+ "<tbody>"
									+ "<tr>"
										+ "<th>문의시간</th>"
										+ "<td>"
											+ new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).format( new Date() )
										+ "</td>"
									+ "</tr>"
									+ "<tr>"
										+ "<th>문의종류</th>"
										+ "<td>" + type + "</td>"
									+ "</tr>"
								+ "</tbody>"
							+ "</table>";
					smtpUtils.setText( text );
					
					log.info( "Sending email" );
					smtpUtils.send();
					
					
					resultMap.put( "code", 200 );
					resultMap.put( "msg", "정상처리 되었습니다." );
					resultMap.put( "boIdx", boardDto.getBoIdx() );
				} else {
					resultMap.put( "code", 0 );
					resultMap.put( "msg", "데이터베이스 오류로 인해 처리되지 않았습니다." );
				}
			}
		} else {
			if( StringUtils.isEmpty( boardDto.getBoTitle() ) ) {
				resultMap.put( "code", 0 );
				resultMap.put( "msg", "제목을 입력해주세요." );
			} else {
				BoardDto board = BoardDto.builder().build();
				board.setBoIdx( boardDto.getBoIdx() );
				board = boardRepository.findBoardOneByBoIdx(board);
				
				
				
				board.setBoTitle( boardDto.getBoTitle() );
				if( StringUtils.isEmpty( boardDto.getBoContent() ) ) {
					board.setBoContent("내용을 등록하지 않았습니다.");
				} else {
					board.setBoContent( boardDto.getBoContent() );
				}
				board.setBoIsNotice( boardDto.getBoIsNotice() );
				
				// file handling
				if( boardDto.getBoFile1file() != null && !boardDto.getBoFile1file().isEmpty() ) {
					UploadFileDto uploadFileDto = fileService.store( boardDto.getBoFile1file() );
					board.setBoFile1( uploadFileDto.getUfIdx() );
				}
				
				int result = boardRepository.updateBoardOneByBoIdx( board );
				
				if( result > 0 ) {
					resultMap.put( "code", 200 );
					resultMap.put( "msg", "정상처리 되었습니다." );
					resultMap.put( "boIdx", boardDto.getBoIdx() );
				} else {
					resultMap.put( "code", 0 );
					resultMap.put( "msg", "데이터베이스 오류로 인해 처리되지 않았습니다." );
				}
			}
		}
		
		return resultMap;
	}

	public UploadFileDto findUploadFileOneByUfIdx(UploadFileDto uploadFileDto) {
		return uploadFileRepository.findOne( uploadFileDto );
	}

	public SiteConfigDto findSiteConfig() {
		return siteConfigRepository.findOne();
	}


}
