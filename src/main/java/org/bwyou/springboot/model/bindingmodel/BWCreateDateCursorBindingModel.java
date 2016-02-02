package org.bwyou.springboot.model.bindingmodel;

import java.util.Date;


public class BWCreateDateCursorBindingModel extends DateCursorBindingModel {
	
	public BWCreateDateCursorBindingModel(){
		after = new Date(0);
		sort = "-createDT";
	}
}
