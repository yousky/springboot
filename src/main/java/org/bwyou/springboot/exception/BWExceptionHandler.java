package org.bwyou.springboot.exception;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bwyou.springboot.viewmodel.ErrorResultViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;


public class BWExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(BWExceptionHandler.class);

	protected Object handleException(HttpServletRequest request, HttpServletResponse response, Locale locale, Exception exraw) {

		WebException ex = null;
		if (exraw instanceof WebException) {
			ex = (WebException)exraw;
		}
		else {
			ex = new WebException(exraw);
		}		
		
		String url = (String) request.getAttribute("javax.servlet.forward.request_uri");	//error-page 등으로 포워딩 되는 경우 원래 uri 확인 하기 위하여 사용
		if(url == null) {
			url = request.getRequestURI();
		}
		
		logger.warn(String.format("%s %s [locale : %s] Exception Cathed! [Exception(%s) : %s]", request.getMethod(), url, locale, ex.getBody().getStatus(), ex.getBody().getMessage()));

		if(url.contains("/api/") || url.contains("/oauth/token") || url.contains("/Token")){	//api 여부는 url로 확인 한다.
			return returnMV4ApiWebException(request, response, locale, ex);
		}
		else {
			return returnMV4WebException(request, response, locale, ex);
		}
	}
	protected ModelAndView returnMV4WebException(HttpServletRequest request, HttpServletResponse response, Locale locale, WebException ex) {
		
		//response.setStatus(ex.getBody().getStatus()); 쓰면 안 됨~

		ModelAndView mv = new ModelAndView();
		mv.addObject("url", request.getRequestURI());
		mv.addObject("exception", ex);
		mv.setViewName("/errors/common");	//TODO /errors/common 이 부분을 외부로 뺄 수 있는 방안 고려 필요
		
        return mv;
	}
	protected ResponseEntity<Object> returnMV4ApiWebException(HttpServletRequest request, HttpServletResponse response, Locale locale, WebException ex) {
		return new ResponseEntity<Object>(new ErrorResultViewModel(ex), HttpStatus.valueOf(ex.getBody().getStatus()));
	}
}
