package org.bwyou.springboot.model.bindingmodel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BWIdPageBindingModel extends PageBindingModel {
	
	public BWIdPageBindingModel(){
		sort = "-id";
	}
}
