package org.bwyou.springboot.model.bindingmodel;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
public class LimitSortBindingModel extends LimitBindingModel {
	//sort는 한 칼럼만이 단독으로 사용 되어야만 한다. 복합 칼럼은 처리 안 됨.
	protected String sort;
}
