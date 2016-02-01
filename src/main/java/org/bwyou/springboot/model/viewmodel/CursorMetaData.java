package org.bwyou.springboot.model.viewmodel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ReflectionUtils;

@Getter
public class CursorMetaData {
	
	private Object before;
	private Object after;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CursorMetaData(Iterable result, String cursorProp) {
    	boolean bDesc = false;
        if (cursorProp.startsWith("-") == true)
        {
            bDesc = true;
            cursorProp = cursorProp.substring(1);
        }
		
		List list = new ArrayList();
		for (Object t : result) {
			list.add(t);
		}
		
		if (list.size() > 0) {
			if (bDesc == true) {
				before = getPropertyValue(list.get(list.size()-1), cursorProp);
				after = getPropertyValue(list.get(0), cursorProp);
			} else {
				after = getPropertyValue(list.get(list.size()-1), cursorProp);
				before = getPropertyValue(list.get(0), cursorProp);
			}
		}
	}
	
	public Object getPropertyValue (Object source, String propertyName) {

	    final BeanWrapper src = new BeanWrapperImpl(source);
		Class<?> clazz = source.getClass();

	    Field field = ReflectionUtils.findField(clazz, propertyName);
	    ReflectionUtils.makeAccessible(field);

	    Object srcValue = src.getPropertyValue(field.getName());
	    
	    return srcValue;
	}
}
