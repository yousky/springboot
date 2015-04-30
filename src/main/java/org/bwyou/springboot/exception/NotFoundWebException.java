package org.bwyou.springboot.exception;

import org.springframework.http.HttpStatus;

import org.bwyou.springboot.viewmodel.WebStatusMessageBody;

public class NotFoundWebException extends WebException {

	private static final long serialVersionUID = 3429505844352644945L;

	public NotFoundWebException(WebStatusMessageBody body) {
		super(HttpStatus.NOT_FOUND, body);
	}

	public NotFoundWebException(Exception ex) {
		super(HttpStatus.NOT_FOUND, ex);
	}

	public NotFoundWebException() {
		super(HttpStatus.NOT_FOUND, new Exception(HttpStatus.NOT_FOUND.getReasonPhrase()));
	}
}
