package org.bwyou.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.bwyou.springboot.viewmodel.WebStatusMessageBody;

public class WebException extends RuntimeException {

	private static final long serialVersionUID = 3584021599514020963L;

	WebStatusMessageBody body;
	BindingResult bindingResult;

	public WebStatusMessageBody getBody() {
		return body;
	}

	public BindingResult getBindingResult() {
		return bindingResult;
	}

	public WebException(HttpStatus status, WebStatusMessageBody body) {
		super(body.getMessage());
		body.setStatus(status.value());
		this.body = body;
	}
	
	public WebException(HttpStatus status, Exception ex) {
		super(ex);
		body = new WebStatusMessageBody("E" + String.format("%03d", status.value())
							, ex.getMessage(), "", "");
		body.setStatus(status.value());
	}
	
	public WebException(HttpStatus status, Exception ex, BindingResult bindingResult) {
		super(ex);
		body = new WebStatusMessageBody("E" + String.format("%03d", status.value())
							, ex.getMessage(), "", "");
		body.setStatus(status.value());
		this.bindingResult = bindingResult;
	}

	public WebException(WebStatusMessageBody body) {
		super(body.getMessage());
		this.body = body;
	}
	
	public WebException(Exception ex) {
		super(ex);
		body = new WebStatusMessageBody("E" + String.format("%03d", HttpStatus.INTERNAL_SERVER_ERROR.value())
							, ex.getMessage(), "", "");
		body.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	public WebException() {
		super();
		body = new WebStatusMessageBody("E" + String.format("%03d", HttpStatus.INTERNAL_SERVER_ERROR.value())
							, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "", "");
		body.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

}
