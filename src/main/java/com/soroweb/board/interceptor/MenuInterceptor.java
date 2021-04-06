package com.soroweb.board.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import com.soroweb.board.constant.AttributeName;
import com.soroweb.board.constant.BcBoardType;
import com.soroweb.board.dto.BoardConfigDto;
import com.soroweb.board.dto.MenuConfigDto;
import com.soroweb.board.dto.ProviderDto;
import com.soroweb.board.dto.SubMenuConfigDto;
import com.soroweb.board.repository.BoardConfigRepository;
import com.soroweb.board.repository.MenuConfigRepository;
import com.soroweb.board.repository.ProviderRepository;
import com.soroweb.board.repository.SubMenuConfigRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MenuInterceptor implements HandlerInterceptor {

	@Autowired
	private MenuConfigRepository menuConfigRepository;
	
	@Autowired
	private BoardConfigRepository boardConfigRepository;
	
	@Autowired
	private SubMenuConfigRepository subMenuConfigRepository;
	
	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private LocaleResolver localeResolver;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if( localeResolver.resolveLocale(request) == null ) {
			localeResolver.setLocale(request, response, Locale.KOREA);
		}
		
		request.setAttribute("i18n", localeResolver.resolveLocale(request).getLanguage() );
		
		log.info( request.getRequestURI() );
		
		if( request.getRequestURL().toString().toUpperCase().indexOf( "/ADMIN" ) >= 0 ) {
			BoardConfigDto boardConfigDto = BoardConfigDto.builder().build();
			
			boardConfigDto.setBcBoardType( BcBoardType.BOARD.getName() );
			request.setAttribute( AttributeName.ADMIN_BOARD_MENU_LIST.getName(), boardConfigRepository.findBoardConfigListByBcBoardType( boardConfigDto ) );
			
			boardConfigDto.setBcBoardType( BcBoardType.PRODUCT.getName() );
			request.setAttribute( AttributeName.ADMIN_PRODUCT_MENU_LIST.getName(), boardConfigRepository.findBoardConfigListByBcBoardType( boardConfigDto ) );
		} else if( request.getRequestURL().toString().toUpperCase().indexOf( "/AJAX" ) < 0 && request.getRequestURL().toString().toUpperCase().indexOf( ".AJAX" ) < 0 ) {
			
			if( !request.getRequestURI().equals("/") 
					&& request.getRequestURI().toUpperCase().indexOf("/SIGNIN") < 0 
					&& request.getRequestURI().toUpperCase().indexOf("/IMAGE") < 0
					&& request.getRequestURI().toUpperCase().indexOf("/MEMBER") < 0
					&& request.getRequestURI().toUpperCase().indexOf("/LANG") < 0
					&& request.getRequestURI().toUpperCase().indexOf("/PAGE/PRIVACY") < 0 
					&& request.getRequestURI().toUpperCase().indexOf("/TEST") < 0) {
				String[] uriSplit = request.getRequestURI().split("/");
				
				SubMenuConfigDto currentSubMenu = SubMenuConfigDto.builder().build();
				currentSubMenu.setSmcCategory( uriSplit[1] );
				currentSubMenu.setSmcUrl( uriSplit[2] );
				if( uriSplit.length > 3 && !uriSplit[1].toUpperCase().equals("BOARD") && !uriSplit[1].toUpperCase().equals("PRODUCT") ) {
					currentSubMenu.setSmcUrl( currentSubMenu.getSmcUrl() + "/" + uriSplit[3] );
				}
				currentSubMenu.setSmcLang( localeResolver.resolveLocale(request).getLanguage() );
				currentSubMenu = subMenuConfigRepository.findOneBySmcCategoryAndSmcUrlLike( currentSubMenu );

				request.setAttribute( "currentSubMenuOne", currentSubMenu );
				
				MenuConfigDto menuConfigDto = MenuConfigDto.builder().build();
				menuConfigDto.setMcIdx( currentSubMenu.getMcIdx() );
				
				MenuConfigDto currentMenu = menuConfigRepository.findOneByMcIdx( menuConfigDto );
				request.setAttribute( "currentMenu", currentMenu );
			}
			
			
			Map<String, List<?>> menuList = new HashMap<String, List<?>>();
			MenuConfigDto menuConfig = MenuConfigDto.builder().build();
			menuConfig.setMcIsUse( 1 );
			menuConfig.setMcLang( localeResolver.resolveLocale(request).getLanguage() );
			List<MenuConfigDto> mainMenuList = menuConfigRepository.findMenuConfigListByMcIsUseOrderByMcOrderAsc( menuConfig );
			log.info( mainMenuList.toString() );
			menuList.put( "mainMenu", mainMenuList );
			
			List<List<SubMenuConfigDto>> subMenuList = new ArrayList<>();
			for( int i=0; i<mainMenuList.size(); i++ ) {
				SubMenuConfigDto subMenuConfigDto = SubMenuConfigDto.builder().build();
				subMenuConfigDto.setMcIdx( mainMenuList.get(i).getMcIdx() );
				subMenuConfigDto.setSmcIsUse( 1 );
				List<SubMenuConfigDto> subMenu = subMenuConfigRepository.findSubmenuConfigListByMcIdxAndSmcIsUseOrderBySmcOrderAsc( subMenuConfigDto );
				subMenuList.add( subMenu );
				
				if( !request.getRequestURI().equals("/") 
						&& request.getRequestURI().toUpperCase().indexOf("/SIGNIN") < 0 
						&& request.getRequestURI().toUpperCase().indexOf("/IMAGE") < 0 
						&& request.getRequestURI().toUpperCase().indexOf("/MEMBER") < 0
						&& request.getRequestURI().toUpperCase().indexOf("/LANG") < 0
						&& request.getRequestURI().toUpperCase().indexOf("/PAGE/PRIVACY") < 0
						&& request.getRequestURI().toUpperCase().indexOf("/TEST") < 0) {
					String[] uriSplit = request.getRequestURI().split("/");
					SubMenuConfigDto currentSubMenu = SubMenuConfigDto.builder().build();
					currentSubMenu.setSmcCategory( uriSplit[1] );
					currentSubMenu.setSmcUrl( uriSplit[2] );
					if( uriSplit.length > 3 && !uriSplit[1].toUpperCase().equals("BOARD") && !uriSplit[1].toUpperCase().equals("PRODUCT") ) {
						currentSubMenu.setSmcUrl( currentSubMenu.getSmcUrl() + "/" + uriSplit[3] );
					}
					currentSubMenu.setSmcLang( localeResolver.resolveLocale(request).getLanguage() );
					currentSubMenu = subMenuConfigRepository.findOneBySmcCategoryAndSmcUrlLike( currentSubMenu );
					request.setAttribute( "currentSubMenuOne", currentSubMenu );
					
					MenuConfigDto menuConfigDto = MenuConfigDto.builder().build();
					menuConfigDto.setMcIdx( currentSubMenu.getMcIdx() );
					
					MenuConfigDto currentMenu = menuConfigRepository.findOneByMcIdx( menuConfigDto );
					
					if( mainMenuList.get(i).getMcIdx() == currentMenu.getMcIdx() ) {
						request.setAttribute( "currentSubMenu", subMenu );
					}
					
					/// logic refactoring need
				}
			}
			menuList.put( "subMenu", subMenuList );
			
			/* footer slider */
			/* 중복제거 START 
			 * 설명 : 홈페이지 주소를 비교해서 중복된다면 로고 제거
			 */
			List<ProviderDto> footerProvierList = providerRepository.findListForFooter();
			int footerCount = footerProvierList.size();
			int index = 0;
			String[] homeageArray = new String[footerCount];
			
			for( int i = 0; i < footerCount; i++ ) {
				boolean flag = false;
				// 홈페이지 배열 값과 비교
				for( int j = 0; j < homeageArray.length; j++ ) {
					if (homeageArray[j] != null) {
						if (homeageArray[j].equals(footerProvierList.get(index).getPvHomepage())) {
							flag = true;
						}
					}
				}
				if (flag) {
					footerProvierList.remove(index);
				} else {
					homeageArray[i] = footerProvierList.get(index).getPvHomepage();
					index++;
				}
			}
			/* 중복제거 END */
			request.setAttribute( "footerProvider", footerProvierList );
			
			request.setAttribute( "menu", menuList );
		}
		
		// GET LOCALE DATA FROM DATABASE
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
