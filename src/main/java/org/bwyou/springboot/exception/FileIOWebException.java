package org.bwyou.springboot.exception;

import org.springframework.http.HttpStatus;
import org.bwyou.springboot.model.viewmodel.WebStatusMessageBody;

public class FileIOWebException extends WebException {

	private static final long serialVersionUID = -5262792219528113220L;

	public FileIOWebException(WebStatusMessageBody body) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, body);
	}

	public FileIOWebException(Exception ex) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}

	public FileIOWebException() {
		super(HttpStatus.INTERNAL_SERVER_ERROR, new Exception("FileIOWebException Occured"));
	}
}
