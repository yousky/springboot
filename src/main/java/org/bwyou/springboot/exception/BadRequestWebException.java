package org.bwyou.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.bwyou.springboot.viewmodel.WebStatusMessageBody;

public class BadRequestWebException extends WebException {

	private static final long serialVersionUID = 3686563228635802937L;

	public BadRequestWebException(WebStatusMessageBody body) {
		super(HttpStatus.BAD_REQUEST, body);
	}

	public BadRequestWebException(Exception ex) {
		super(HttpStatus.BAD_REQUEST, ex);
	}

	public BadRequestWebException() {
		super(HttpStatus.BAD_REQUEST, new Exception(HttpStatus.BAD_REQUEST.getReasonPhrase()));
	}

	public BadRequestWebException(BindingResult bindingResult) {
		super(HttpStatus.BAD_REQUEST, new Exception(HttpStatus.BAD_REQUEST.getReasonPhrase()), bindingResult);
	}
}
