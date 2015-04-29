package org.bwyou.springboot.controller;

import java.io.Serializable;
import java.util.List;

import org.bwyou.springboot.service.BWEntityService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.BindingResult;

public class BWRestController<TEntity, ID extends Serializable> {

	protected BWEntityService<TEntity> entityService;
	
	public BWRestController(BWEntityService<TEntity> entityService) {
		this.entityService = entityService;
	}
	
	protected List<TEntity> BaseGetList() {
    	return entityService.GetList();
    }
	
	protected List<TEntity> BaseGetList(Specification<TEntity> spec) {
    	return entityService.GetList(spec);
    }
	
	protected TEntity BaseGet(int id) {
    	return entityService.Get(id);
    }
	
	protected TEntity BaseGet(Specification<TEntity> spec) {
    	return entityService.Get(spec);
    }
	
	protected TEntity BaseValidPost(TEntity entity, BindingResult result) {
    	return entityService.ValidAndCreate(entity, result);
    }
	
	protected TEntity BaseValidPut(int id, TEntity entity, BindingResult result) {
    	return entityService.ValidAndUpdate(id, entity, result);
    }
	
	protected int BaseValidDelete(int id, BindingResult result) {
    	return entityService.ValidAndDelete(id, result);
    }
    
    
	
}
