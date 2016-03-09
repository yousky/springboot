package org.bwyou.springboot.service;

import java.util.List;

import org.bwyou.springboot.model.bindingmodel.BWCursorBindingModel;
import org.bwyou.springboot.model.bindingmodel.PageBindingModel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.BindingResult;

public interface BWEntityService<TEntity> {
	List<TEntity> GetList();
	List<TEntity> GetList(String sort);
	Page<TEntity> GetList(String sort, int pageNumber, int pageSize);
	Page<TEntity> GetList(PageBindingModel pageBM);
	Page<TEntity> GetList(BWCursorBindingModel cursorBM) throws Exception;
	List<TEntity> GetFilteredList(Specification<TEntity> spec);
	List<TEntity> GetFilteredList(Specification<TEntity> spec, String sort);
	Page<TEntity> GetFilteredList(Specification<TEntity> spec, String sort, int pageNumber, int pageSize);
	Page<TEntity> GetFilteredList(Specification<TEntity> spec, PageBindingModel pageBM);
	Page<TEntity> GetFilteredList(Specification<TEntity> spec, BWCursorBindingModel cursorBM) throws Exception;
	
    TEntity Get(int id);
    TEntity Get(Specification<TEntity> spec);
    TEntity ValidAndCreate(TEntity entity, BindingResult bindingResult);
    List<TEntity> ValidAndCreate(List<TEntity> entities, BindingResult bindingResult);
    TEntity ValidAndUpdate(int id, TEntity entity, BindingResult bindingResult);
    int ValidAndDelete(int id, BindingResult bindingResult);
}
