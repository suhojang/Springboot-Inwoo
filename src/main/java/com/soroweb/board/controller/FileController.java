package com.soroweb.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.soroweb.board.dto.UploadFileDto;
import com.soroweb.board.service.FileService;
import com.soroweb.board.util.MediaUtils;

@Controller
public class FileController {

	@Autowired
	private FileService imageService;
	
	@GetMapping( "/file/{ufIdx}" )
	@ResponseBody
	public ResponseEntity<?> serveFile( @PathVariable Long ufIdx ) {
		try {
			UploadFileDto uploadedFile = imageService.load( ufIdx );
			HttpHeaders headers = new HttpHeaders();
			
			String fileName = uploadedFile.getUfFileName();
			headers.add( HttpHeaders.CONTENT_DISPOSITION, "attachment); filename=\"" + new String( fileName.getBytes("UTF-8"), "ISO-8859-1" ) + "\"" );
			
			if( MediaUtils.containsImageMediaType( uploadedFile.getUfContentType() ) ) {
				headers.setContentType( MediaType.valueOf( uploadedFile.getUfContentType() ) );
			} else {
				headers.setContentType( MediaType.APPLICATION_OCTET_STREAM );
			}
			
			Resource resource = imageService.loadAsResource( uploadedFile.getUfSaveFileName() );
			return ResponseEntity.ok().headers( headers ).body( resource );
		} catch ( Exception e ) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping( "/image/{ufIdx}" )
	@ResponseBody
	public ResponseEntity<?> serveImage( @PathVariable Long ufIdx ) {
		try {
			UploadFileDto uploadedFile = imageService.load( ufIdx );
			HttpHeaders headers = new HttpHeaders();
			
			String fileName = uploadedFile.getUfFileName();
			headers.add( HttpHeaders.CONTENT_DISPOSITION, "attachment); filename=\"" + new String( fileName.getBytes("UTF-8"), "ISO-8859-1" ) + "\"" );
			
			if( MediaUtils.containsImageMediaType( uploadedFile.getUfContentType() ) ) {
				headers.setContentType( MediaType.valueOf( uploadedFile.getUfContentType() ) );
			} else {
				headers.setContentType( MediaType.APPLICATION_OCTET_STREAM );
			}
			
			Resource resource = imageService.loadAsResource( uploadedFile.getUfSaveFileName() );
			return ResponseEntity.ok().headers( headers ).body( resource );
		} catch ( Exception e ) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping( { "/image", "/file" } )
	@ResponseBody
	public ResponseEntity<?> handleFileUpload( @RequestParam("file") MultipartFile file ) {
		try {
			UploadFileDto uploadedFile = imageService.store( file );
			return ResponseEntity.ok().body("/image/" + uploadedFile.getUfIdx() );
		} catch ( Exception e ) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
	
	
}
