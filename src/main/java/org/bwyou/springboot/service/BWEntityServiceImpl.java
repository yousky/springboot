package org.bwyou.springboot.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.kaczmarzyk.spring.data.jpa.domain.Conjunction;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;

import org.bwyou.springboot.annotation.Filterable;
import org.bwyou.springboot.annotation.Updatable;
import org.bwyou.springboot.dao.BWRepository;
import org.bwyou.springboot.model.BWModel;
import org.bwyou.springboot.model.bindingmodel.BWCursorBindingModel;
import org.bwyou.springboot.model.bindingmodel.PageBindingModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class BWEntityServiceImpl<TEntity extends BWModel> implements BWEntityService<TEntity> {

	protected BWRepository<TEntity, Integer> daoRepository;
	
	public BWEntityServiceImpl(BWRepository<TEntity, Integer> daoRepository) {
		this.daoRepository = daoRepository;
	}
	
	@Transactional
	@Override
	public List<TEntity> GetList() {
    	return daoRepository.findAll();
	}

	@Transactional
	@Override
	public List<TEntity> GetList(String sort) {
		return daoRepository.findAll(GetOrderClause(sort));
	}

	@Transactional
	@Override
	public Page<TEntity> GetList(String sort, int pageNumber, int pageSize) {
		Pageable pageSpecification = new PageRequest(pageNumber-1, pageSize, GetOrderClause(sort));
		return daoRepository.findAll(pageSpecification);
	}

	@Transactional
	@Override
	public Page<TEntity> GetList(PageBindingModel pageBM) {
		return GetList(pageBM.getSort(), pageBM.getPage() == null? 0:pageBM.getPage(), pageBM.getLimit());
	}

	@Transactional
	@Override
	public Page<TEntity> GetList(BWCursorBindingModel cursorBM) throws Exception {
		Pageable pageSpecification = new PageRequest(0, cursorBM.getLimit(), GetOrderClause(cursorBM.getSort()));
		Specification<TEntity> spec = cursorBM.<TEntity>GetSpecification();
		return daoRepository.findAll(spec, pageSpecification);
	}

	@Transactional
	@Override
	public List<TEntity> GetFilteredList(Specification<TEntity> spec) {
		return daoRepository.findAll(spec);
	}

	@Override
	public List<TEntity> GetFilteredList(Specification<TEntity> spec, String sort) {
		return daoRepository.findAll(spec, GetOrderClause(sort));
	}

	@Transactional
	@Override
	public Page<TEntity> GetFilteredList(Specification<TEntity> spec, String sort, int pageNumber, int pageSize) {
		Pageable pageSpecification = new PageRequest(pageNumber-1, pageSize, GetOrderClause(sort));
		return daoRepository.findAll(spec, pageSpecification);
	}

	@Transactional
	@Override
	public Page<TEntity> GetFilteredList(Specification<TEntity> spec, PageBindingModel pageBM) {
		return GetFilteredList(spec, pageBM.getSort(), pageBM.getPage() == null? 0:pageBM.getPage(), pageBM.getLimit());
	}

	@Transactional
	@Override
	public Page<TEntity> GetFilteredList(Specification<TEntity> spec, BWCursorBindingModel cursorBM) throws Exception {
		Pageable pageSpecification = new PageRequest(0, cursorBM.getLimit(), GetOrderClause(cursorBM.getSort()));
		spec = Specifications.where(spec).and(cursorBM.<TEntity>GetSpecification());
		return daoRepository.findAll(spec, pageSpecification);
	}

	@Transactional
	@Override
	public TEntity Get(int id) {
		return daoRepository.findOne(id);
	}

	@Transactional
	@Override
	public TEntity Get(Specification<TEntity> spec) {
		return daoRepository.findOne(spec);
	}

	@Transactional
	@Override
	public TEntity ValidAndCreate(TEntity entity, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return null;
        }
		return daoRepository.saveAndFlush(entity);
	}

	@Transactional
	@Override
	public List<TEntity> ValidAndCreate(List<TEntity> entities, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return null;
        }
		List<TEntity> result = daoRepository.save(entities);
		daoRepository.flush();
		return result;
	}

	@Transactional
	@Override
	public TEntity ValidAndUpdate(int id, TEntity entity, BindingResult bindingResult) {
		return ValidAndUpdate(id, entity, null, bindingResult);
	}

	@Transactional
	@Override
	public TEntity ValidAndUpdate(int id, TEntity entity, ArrayList<String> nullUpdatableFields, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return null;
        }
		if(entity.getId() != null && id != entity.getId()){
			bindingResult.addError(new FieldError(bindingResult.getObjectName(), "id","mismatch"));	//TODO 메세지 다국어 처리
            return null;
		}
		TEntity target = daoRepository.findOne(id);
		if(target == null){
			bindingResult.addError(new FieldError(bindingResult.getObjectName(), "id","Entity null"));	//TODO 메세지 다국어 처리
            return null;
		}
		
		copyNonNullAndUpdatableProperties(entity, target, nullUpdatableFields);
		target.setUpdateDT(new Date());
		return daoRepository.saveAndFlush(target);
	}

	@Transactional
	@Override
	public int ValidAndDelete(int id, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return -1;
        }
		if(Get(id) == null){
			bindingResult.addError(new FieldError(bindingResult.getObjectName(), "id","Entity null"));	//TODO 메세지 다국어 처리
            return -1;
		}
		daoRepository.delete(id);
		
		return 1;
	}
	
	public void copyNonNullAndUpdatableProperties(Object src, Object target, ArrayList<String> nullUpdatableFields) {
	    BeanUtils.copyProperties(src, target, getNullOrNotUpdatablePropertyNames(src, nullUpdatableFields));
	}
	
	/**
	 * updatable annotation이 없거나 null 인 property name들 반환
	 * @param source
	 * @return
	 */
	public String[] getNullOrNotUpdatablePropertyNames (Object source, ArrayList<String> nullUpdatableFields) {
		
	    final BeanWrapper src = new BeanWrapperImpl(source);

	    Set<String> emptyNames = new HashSet<String>();
	    
		ReflectionUtils.doWithFields(source.getClass(), new FieldCallback() {
			@Override
			public void doWith(Field field) throws IllegalAccessException {
				ReflectionUtils.makeAccessible(field);
				if (field.getAnnotation(Updatable.class) == null) {
					emptyNames.add(field.getName());
				} else {
			        Object srcValue = src.getPropertyValue(field.getName());
			        if (srcValue == null) {
			        	if(nullUpdatableFields == null || nullUpdatableFields.contains(field.getName()) == false){
			        		emptyNames.add(field.getName());
			        	}
			        }
		    	}
			}
		});

	    String[] result = new String[emptyNames.size()];
	    return emptyNames.toArray(result);
	}
	
	protected Specification<TEntity> GetWhereClause(TEntity entity){

		final BeanWrapper src = new BeanWrapperImpl(entity);
	    Collection<Specification<TEntity>> innerSpecs = new ArrayList<Specification<TEntity>>();
		
		for (Field field : getAllFields(new ArrayList<Field>(), entity.getClass())) {
			//TODO 부모필드까지 올라 갔을 때 annotation 상속이나 override 고려 안 되어 있음.. 테스트 필요.
			if (field.isAnnotationPresent(Filterable.class) == true) {
				String name = field.getName();
				Object srcValue;
				try {
					srcValue = src.getPropertyValue(name);
				} catch (Throwable e) {
					e.printStackTrace();
					srcValue = null;
				}
				
				if (srcValue != null) {
					innerSpecs.add(new Equal<>(name, new String[]{srcValue.toString()}));
				}
			}
		}
		
	    return new Conjunction<>(innerSpecs);
		
	}
	
	protected Sort GetOrderClause(String sort){
		String[] sortInfoArray = sort.split(",");

		List<Order> orders = new ArrayList<>();
        for (String sortInfo : sortInfoArray)
        {
        	String sortProp = sortInfo;
        	boolean bDesc = false;
            if (sortInfo.startsWith("-") == true)
            {
                bDesc = true;
                sortProp = sortInfo.substring(1);
            }

            Order order = bDesc == false ? new Order(Direction.ASC, sortProp) : new Order(Direction.DESC, sortProp);
            
            orders.add(order);
        }

        return new Sort(orders);
		
	}
	
	public List<Field> getAllFields(List<Field> fields, Class<?> type) {
	    fields.addAll(Arrays.asList(type.getDeclaredFields()));

	    if (type.getSuperclass() != null) {
	        fields = getAllFields(fields, type.getSuperclass());
	    }

	    return fields;
	}
}
