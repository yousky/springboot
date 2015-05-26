package org.bwyou.springboot.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class BWModel  implements Serializable{

	private static final long serialVersionUID = -8777196809521557362L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @Column(nullable = false, updatable=false)
	private Date createDT;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @Column(nullable = false)
	private Date updateDT;
	
    @JsonProperty
	public Date getCreateDT() {
		return createDT;
	}

    @JsonIgnore
	public void setCreateDT(Date createDT) {
		this.createDT = createDT;
	}

	@JsonProperty
	public Date getUpdateDT() {
		return updateDT;
	}

	@JsonIgnore
	public void setUpdateDT(Date updateDT) {
		this.updateDT = updateDT;
	}

	public BWModel() {
		createDT = new Date();
		updateDT = new Date();
	}
}
