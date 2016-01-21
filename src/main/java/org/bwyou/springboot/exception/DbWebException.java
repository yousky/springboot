package org.bwyou.springboot.exception;

import org.springframework.http.HttpStatus;
import org.bwyou.springboot.model.viewmodel.WebStatusMessageBody;

public class DbWebException extends WebException {

	private static final long serialVersionUID = -200842438705078975L;

	public DbWebException(WebStatusMessageBody body) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, body);
	}

	public DbWebException(Exception ex) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}

	public DbWebException() {
		super(HttpStatus.INTERNAL_SERVER_ERROR, new Exception("DbWebException Occured"));
	}
}
