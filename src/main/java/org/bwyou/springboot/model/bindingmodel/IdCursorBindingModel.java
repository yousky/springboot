package org.bwyou.springboot.model.bindingmodel;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.bwyou.springboot.dao.spec.After;
import org.bwyou.springboot.dao.spec.Before;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;


@Data
@EqualsAndHashCode(callSuper=false)
public class IdCursorBindingModel<ID extends Serializable & Comparable<? super ID>> extends BWCursorBindingModel {
	//기준 점 보다 과거를 의미
	protected ID before;
	//기준 점 보다 미래를 의미. 일반적으로 최신을 읽으려고 하기 때문에 여기에 기본값을 처리
	protected ID after;
	
	@Override
	public <T> Specification<T> GetSpecification() throws Exception {
		
		String colName = sort.indexOf("-") != 0 ? sort : sort.substring(1); 
		Specification<T> spec = null;
		if (before == null && after != null) {
			Specification<T> specAfter = new After<T, ID>(colName, new String[] {after.toString()});
			spec = specAfter;
		}
		else if (before != null && after == null) {
			Specification<T> specBefore = new Before<T, ID>(colName, new String[] {before.toString()});
			spec = specBefore;
		}
		else if (before != null && after != null) {
			Specification<T> specAfter = new After<T, ID>(colName, new String[] {after.toString()});
			Specification<T> specBefore = new Before<T, ID>(colName, new String[] {before.toString()});
			spec = Specifications.where(specAfter).and(specBefore);;
		}
		else{
			throw new Exception("before == null && after == null");
		}
		return spec;
	}
}
