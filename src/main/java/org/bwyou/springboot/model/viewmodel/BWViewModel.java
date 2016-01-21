package org.bwyou.springboot.model.viewmodel;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class BWViewModel{
	
	private Integer id;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date createDT;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date updateDT;
    
}
