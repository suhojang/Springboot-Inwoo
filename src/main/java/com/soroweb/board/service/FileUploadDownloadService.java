package com.soroweb.board.service;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.soroweb.board.exception.FileDownloadException;
import com.soroweb.board.exception.FileUploadException;
import com.soroweb.board.property.FileUploadProperties;

@Service
public class FileUploadDownloadService {

	private final Path fileLocation;
	
	@Autowired
	public FileUploadDownloadService( FileUploadProperties prop ) {
		this.fileLocation = Paths.get( prop.getUploadDir() )
				.toAbsolutePath().normalize();
		
		try {
			Files.createDirectories( this.fileLocation );
		} catch( Exception e ) {
			throw new FileUploadException( "Not create upload directory", e );
		}
	}
	
	public String storeFile( MultipartFile file ) {
		String fileName = StringUtils.cleanPath( file.getOriginalFilename() );
		
		try {
			if( fileName.contains( ".." ) ) throw new FileUploadException( "파일명에 부적합 문자가 포함되어 있습니다." + fileName );
			
			Path targetLocation = this.fileLocation.resolve( fileName );
			
			Files.copy( file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING );
			
			return fileName;
		} catch ( Exception e ) {
			throw new FileUploadException("[" + fileName + "] Upload Failure" );
		}
	}
	
	public Resource loadFileAsResource( String fileName ) {
		try {
			Path filePath = this.fileLocation.resolve( fileName ).normalize();
			Resource resource = new UrlResource( filePath.toUri() );
			
			if( resource.exists() ) {
				return resource;
			} else {
				throw new FileDownloadException( fileName + " file not found" );
			}
		} catch ( MalformedURLException e ) {
			throw new FileDownloadException( fileName + " file not found." );
		}
	}
}
