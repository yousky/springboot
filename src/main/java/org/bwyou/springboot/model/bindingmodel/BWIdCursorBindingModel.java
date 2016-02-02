package org.bwyou.springboot.model.bindingmodel;


public class BWIdCursorBindingModel extends IdCursorBindingModel<Integer> {
	
	public BWIdCursorBindingModel(){
		after = -1;
		sort = "-id";
	}
}
