package org.bwyou.springboot.viewmodel;

import lombok.Data;

@Data
public class ValidationObjectError {

	private String objectName;
	private String errorMessage;
	
	public ValidationObjectError(String objectName, String errorMessage)
    {
        this.objectName = objectName;
        this.errorMessage = errorMessage;
    }
}
