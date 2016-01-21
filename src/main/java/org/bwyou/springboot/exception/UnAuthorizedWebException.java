package org.bwyou.springboot.exception;

import org.springframework.http.HttpStatus;
import org.bwyou.springboot.model.viewmodel.WebStatusMessageBody;

public class UnAuthorizedWebException extends WebException {

	private static final long serialVersionUID = 6522342284597007805L;

	public UnAuthorizedWebException(WebStatusMessageBody body) {
		super(HttpStatus.UNAUTHORIZED, body);
	}

	public UnAuthorizedWebException(Exception ex) {
		super(HttpStatus.UNAUTHORIZED, ex);
	}

	public UnAuthorizedWebException() {
		super(HttpStatus.UNAUTHORIZED, new Exception(HttpStatus.UNAUTHORIZED.getReasonPhrase()));
	}
}
