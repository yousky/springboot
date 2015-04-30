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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
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
	public List<TEntity> GetList(Specification<TEntity> spec) {
		return daoRepository.findAll(spec);
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
	public TEntity ValidAndUpdate(int id, TEntity entity, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return null;
        }
		if(id != entity.getId()){
			bindingResult.addError(new FieldError("", "Id","mismatch"));
            return null;
		}
		if(Get(entity.getId()) == null){
			bindingResult.addError(new FieldError("", "Entity","null"));
            return null;
		}
		TEntity target = daoRepository.findOne(id);
		copyNonNullAndUpdatableProperties(entity, target);
		target.setUpdateDT(new Date());
		return daoRepository.saveAndFlush(target);
	}

	@Transactional
	@Override
	public int ValidAndDelete(int id, BindingResult bindingResult) {
		if(Get(id) == null){
			bindingResult.addError(new FieldError("", "Entity","null"));
            return -1;
		}
		daoRepository.delete(id);
		
		return 1;
	}
	
	public void copyNonNullAndUpdatableProperties(Object src, Object target) {
	    BeanUtils.copyProperties(src, target, getNullOrNotUpdatablePropertyNames(src));
	}
	
	/**
	 * updatable annotation이 없거나 null 인 property name들 반환
	 * @param source
	 * @return
	 */
	public String[] getNullOrNotUpdatablePropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

	    Set<String> emptyNames = new HashSet<String>();
	    for(java.beans.PropertyDescriptor pd : pds) {
	    	if (pd.getReadMethod().getAnnotation(Updatable.class) == null) {
	    		emptyNames.add(pd.getName());
	    	} else {
		        Object srcValue = src.getPropertyValue(pd.getName());
		        if (srcValue == null) {
		        	emptyNames.add(pd.getName());
		        }
	    	}
	    }
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
	
	public List<Field> getAllFields(List<Field> fields, Class<?> type) {
	    fields.addAll(Arrays.asList(type.getDeclaredFields()));

	    if (type.getSuperclass() != null) {
	        fields = getAllFields(fields, type.getSuperclass());
	    }

	    return fields;
	}
}
