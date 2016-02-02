package org.bwyou.springboot.model.bindingmodel;

import org.springframework.data.jpa.domain.Specification;


public abstract class BWCursorBindingModel extends LimitSortBindingModel {
	public abstract <T> Specification<T> GetSpecification() throws Exception;
}
