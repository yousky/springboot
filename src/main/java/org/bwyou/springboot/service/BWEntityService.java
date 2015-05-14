package org.bwyou.springboot.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.BindingResult;

public interface BWEntityService<TEntity> {
	List<TEntity> GetList();
//	List<Page<TEntity>> GetList(String sort, int pageNumber, int pageSize);
	List<TEntity> GetList(Specification<TEntity> spec);
//	List<Page<TEntity>> GetFilteredList(TEntity entity, String sort, int pageNumber, int pageSize);
	
    TEntity Get(int id);
    TEntity Get(Specification<TEntity> spec);
    TEntity ValidAndCreate(TEntity entity, BindingResult bindingResult);
    List<TEntity> ValidAndCreate(List<TEntity> entities, BindingResult bindingResult);
    TEntity ValidAndUpdate(int id, TEntity entity, BindingResult bindingResult);
    int ValidAndDelete(int id, BindingResult bindingResult);
}
