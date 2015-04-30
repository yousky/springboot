package org.bwyou.springboot.controller;

import java.io.Serializable;
import java.util.List;

import org.bwyou.springboot.exception.BadRequestWebException;
import org.bwyou.springboot.exception.NotFoundWebException;
import org.bwyou.springboot.service.BWEntityService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.BindingResult;

public class BWRestController<TEntity, ID extends Serializable> {

	protected BWEntityService<TEntity> entityService;
	
	public BWRestController() {
		
	}
	
	public BWRestController(BWEntityService<TEntity> entityService) {
		this.entityService = entityService;
	}
	
	protected List<TEntity> BaseGetList() {
    	List<TEntity> result = entityService.GetList();
    	
    	if (result != null) {
			return result;
		}
    	
    	throw new NotFoundWebException();
    	
    }
	
	protected List<TEntity> BaseGetList(Specification<TEntity> spec) {
		List<TEntity> result = entityService.GetList(spec);
    	
    	if (result != null) {
			return result;
		}
    	
    	throw new NotFoundWebException();
    }
	
	protected TEntity BaseGet(int id) {
    	TEntity result = entityService.Get(id);
    	
    	if (result != null) {
			return result;
		}
    	
    	throw new NotFoundWebException();
    }
	
	protected TEntity BaseGet(Specification<TEntity> spec) {
		TEntity result = entityService.Get(spec);
    	
    	if (result != null) {
			return result;
		}
    	
    	throw new NotFoundWebException();
    }
	
	protected TEntity BaseValidPost(TEntity entity, BindingResult bindingResult) {
		TEntity result = entityService.ValidAndCreate(entity, bindingResult);
    	
    	if (result != null) {
			return result;
		}
    	
    	throw new BadRequestWebException(bindingResult);
    }
	
	protected TEntity BaseValidPut(int id, TEntity entity, BindingResult bindingResult) {
		TEntity result = entityService.ValidAndUpdate(id, entity, bindingResult);
    	
    	if (result != null) {
			return result;
		}
    	
    	throw new BadRequestWebException(bindingResult);
    }
	
	protected int BaseValidDelete(int id, BindingResult bindingResult) {
		int result = entityService.ValidAndDelete(id, bindingResult);
    	
    	if (result > 0) {
			return result;
		}
    	
    	throw new BadRequestWebException(bindingResult);
    }
    
    
	
}
