package com.soroweb.board.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.soroweb.board.dto.UploadFileDto;
import com.soroweb.board.repository.UploadFileRepository;
import com.soroweb.board.util.UploadFileUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {
	
	private final Path rootLocation;
	
	@Autowired
	public FileService(String uploadPath) {
		this.rootLocation = Paths.get( uploadPath );
	}
	
	@Autowired
	private UploadFileRepository uploadFileRepository;
	
	public Stream<Long> loadAll() {
		List<UploadFileDto> files = uploadFileRepository.findAll();
		return files.stream().map( file -> file.getUfIdx() );
	}
	
	public UploadFileDto load( Long ufIdx ) {
		UploadFileDto uploadFileDto = UploadFileDto.builder().build();
		uploadFileDto.setUfIdx( ufIdx );
		return uploadFileRepository.findOne( uploadFileDto );
	}
	
	public Resource loadAsResource( String ufFileName ) throws Exception {
		try {
			
			if( ufFileName.toCharArray()[0] == '/' ) {
				ufFileName = ufFileName.substring(1);
			}
			Path file = loadPath( ufFileName );
			Resource resource = new UrlResource( file.toUri() );
			if( resource.exists() || resource.isReadable() ) {
				return resource;
			} else {
				throw new Exception( "Could not read file : " + ufFileName );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new Exception( "Could not read file : " + ufFileName );
		}
	}
	
	private Path loadPath( String ufFileName ) {
		return rootLocation.resolve( ufFileName );
	}
	
	public UploadFileDto store( MultipartFile file ) throws Exception {
		try {
			if( file.isEmpty() ) {
				throw new Exception( "Failed to store empty file " + file.getOriginalFilename() );
			}
			
			String saveFileName = UploadFileUtils.fileSave( rootLocation.toString(), file );
			
			if( saveFileName.toCharArray()[0] == '/' ) {
				saveFileName = saveFileName.substring(1);
			}
			
			Resource resource = loadAsResource( saveFileName );
			
			UploadFileDto saveFile = UploadFileDto.builder().build();
			saveFile.setUfSaveFileName( saveFileName );
			saveFile.setUfFileName( file.getOriginalFilename() );
			saveFile.setUfContentType( file.getContentType() );
			saveFile.setUfFilePath( rootLocation.toString().replace( File.pathSeparatorChar, '/' ) + File.separator + saveFileName );
			saveFile.setUfFileSize( resource.contentLength() );
			saveFile.setUfEnrolDt( LocalDateTime.now() );
			
			uploadFileRepository.save( saveFile );
			
			UploadFileDto uploaded = UploadFileDto.builder().build();
			uploaded.setUfIdx( saveFile.getUfIdx() );
			
			return uploadFileRepository.findOne( uploaded ); 
			
		} catch ( IOException e ) {
			throw new Exception( "Filed to store file " + file.getOriginalFilename(), e );
		}
	}
}
