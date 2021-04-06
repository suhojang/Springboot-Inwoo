package com.soroweb.board.util;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SmtpUtils {
	
	private JavaMailSender sender;
	private MimeMessage message;
	private MimeMessageHelper messageHelper;
	
	public SmtpUtils( JavaMailSender sender ) throws MessagingException {
		this.sender = sender;
		message = sender.createMimeMessage();
		messageHelper = new MimeMessageHelper( message, true, "UTF-8" );
	}
	
	public void setFrom( String email, String name ) throws UnsupportedEncodingException, MessagingException {
		messageHelper.setFrom( email, name );
	}
	
	public void setTo( String email ) throws MessagingException {
		messageHelper.setTo( email );
	}
	
	public void setSubject( String subject ) throws MessagingException {
		messageHelper.setSubject( subject );
	}
	
	public void setText( String text ) throws MessagingException {
		messageHelper.setText( text, true );
	}
	
	public void send() {
		try {
			sender.send( message );
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}

}
