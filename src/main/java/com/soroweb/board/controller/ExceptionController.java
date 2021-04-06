package com.soroweb.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.soroweb.board.constant.AttributeName;
import com.soroweb.board.constant.ExceptionType;
import com.soroweb.board.exception.NotExistBoardDataException;
import com.soroweb.board.exception.NotExistBoardTableException;
import com.soroweb.board.exception.NotMatchUsernameException;
import com.soroweb.board.exception.UnusingBoardAccessException;
import com.soroweb.board.exception.UpdateFailureException;

@Controller
public class ExceptionController {

	final private String DEFAULT_PAGE = "error/defaultErrorPage";

	@ExceptionHandler(value = NotExistBoardTableException.class)
	public String notExistboardConfigDataError( Model model ) {
		model.addAttribute( AttributeName.EXCEPTION_TYPE.getName(), ExceptionType.NOT_EXIST_BOARD_TABLE_EXCEPTION.getName() );
		return DEFAULT_PAGE;
	}
	
	@ExceptionHandler(value = NotMatchUsernameException.class)
	public String notUserMatchError( Model model ) {
		model.addAttribute( AttributeName.EXCEPTION_TYPE.getName(), ExceptionType.NOT_MATCH_USERNAME_EXCEPTION.getName() );
		return DEFAULT_PAGE;
	}
	
	@ExceptionHandler(value = NotExistBoardDataException.class)
	public String notExistBoardDataError( Model model ) {
		model.addAttribute( AttributeName.EXCEPTION_TYPE.getName(), ExceptionType.NOT_EXIST_BOARD_DATA_EXCEPTION.getName() );
		return DEFAULT_PAGE;
	}
	
	@ExceptionHandler(value = UpdateFailureException.class )
	public String updateFailureError( Model model ) {
		model.addAttribute( AttributeName.EXCEPTION_TYPE.getName(), ExceptionType.UPDATE_FAILURE_EXCEPTION.getName() );
		return DEFAULT_PAGE;
	}
	
	@ExceptionHandler(value = UnusingBoardAccessException.class )
	public String unusingBoardAccessError( Model model ) {
		model.addAttribute( AttributeName.EXCEPTION_TYPE.getName(), ExceptionType.UNUSING_BOARD_ACCESS_EXCEPTION.getName() );
		return DEFAULT_PAGE;
	}
}
