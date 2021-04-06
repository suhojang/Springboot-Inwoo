package com.soroweb.board.util;

import java.util.Arrays;
import java.util.regex.Pattern;

public class StringUtils {
	public static String EMPTY = "";
	public static String REGEX_REMOVE_SPACE = "\\p{Z}";
	public static String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
	public static String ZERO = "0";
	public static String ONE = "0";

	public static boolean isNumeric( String s ) {
		try {
			Double.parseDouble( s );
			return true;
		} catch ( NumberFormatException e ) {
			return false;
		}
	}
	
	public static String removeSpace( String s ) {
		return s.replaceAll(REGEX_REMOVE_SPACE, "");
	}
	
	public static boolean isEmpty( String s ) {
		if( s == null || removeSpace(s).equals("") || removeSpace(s).length() == 0 ) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isEmpty( Long l ) {
		String s = String.valueOf( l );
		return isEmpty( s );
	}

	public static boolean isEmpty(int i) {
		return isEmpty( String.valueOf( i ) );
	}
	
	public static boolean isEmail( String s ) {
		return Pattern.matches( REGEX_EMAIL, s );
	}
	
	public static String lpad( String s, int pad, String strChar ) {
		String strResult = "";
		
		StringBuilder sbAddChar = new StringBuilder();
		for( int i=s.length(); i<pad; i++ ) {
			sbAddChar.append(strChar);
		}
		
		strResult = sbAddChar + s;
		return strResult;
	}
	
	public static String rpad( String s, int pad, String strChar ) {
		String strResult = "";
		
		StringBuilder sbAddChar = new StringBuilder();
		for( int i=s.length(); i<pad; i++ ) {
			sbAddChar.append(strChar);
		}
		
		strResult = s + sbAddChar;
		return strResult;
	}
	
	public static String replaceAll( String s, String cng, String tok ) {
		String t = s;
		while( t.indexOf( cng ) >= 0 ) {
			t = t.replace( cng, tok );
		}
		return t;
	}
	
	public static boolean isTrue( String s ) {
		if( s == null ) return false;
		return Arrays.asList( "ON", "Y", "YES", "1" ).contains( s.toUpperCase() );
	}

}
