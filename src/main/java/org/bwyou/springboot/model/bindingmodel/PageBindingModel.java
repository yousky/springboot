package org.bwyou.springboot.model.bindingmodel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PageBindingModel extends LimitSortBindingModel {
	protected Integer pageNumber = 1;	//1부터 시작
}
