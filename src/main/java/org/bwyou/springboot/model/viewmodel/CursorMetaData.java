package org.bwyou.springboot.model.viewmodel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.bwyou.springboot.model.bindingmodel.LimitSortBindingModel;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.util.ReflectionUtils;

@Getter
public class CursorMetaData {
	
	private Object before;
	private Object after;
	private long totalItemCount;
	private int limit;
	private boolean isDescending;
	
	@SuppressWarnings("rawtypes")
	public CursorMetaData(Iterable result, LimitSortBindingModel lsBM) {
		this(result, lsBM.getSort(), lsBM.getLimit());
	}
	
	@SuppressWarnings("rawtypes")
	public CursorMetaData(Page result, String cursorProp) {
		this((Iterable)result, cursorProp, result.getSize());
		totalItemCount = result.getTotalElements();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CursorMetaData(Iterable result, String cursorProp, int limit) {
		this.isDescending = false;
        if (cursorProp.startsWith("-") == true)
        {
        	this.isDescending = true;
            cursorProp = cursorProp.substring(1);
        }
		
		List list = new ArrayList();
		for (Object t : result) {
			list.add(t);
		}
		
		if (list.size() > 0) {
			if (this.isDescending == true) {
				before = getPropertyValue(list.get(list.size()-1), cursorProp);
				after = getPropertyValue(list.get(0), cursorProp);
			} else {
				after = getPropertyValue(list.get(list.size()-1), cursorProp);
				before = getPropertyValue(list.get(0), cursorProp);
			}
		}
		
		this.totalItemCount = list.size();
		this.limit = limit;
	}
	
	private Object getPropertyValue (Object source, String propertyName) {

	    final BeanWrapper src = new BeanWrapperImpl(source);
		Class<?> clazz = source.getClass();

	    Field field = ReflectionUtils.findField(clazz, propertyName);
	    ReflectionUtils.makeAccessible(field);

	    Object srcValue = src.getPropertyValue(field.getName());
	    
	    return srcValue;
	}
}
