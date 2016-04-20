package org.bwyou.springboot.model.viewmodel;

import org.bwyou.springboot.model.bindingmodel.LimitSortBindingModel;
import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class CursorResultViewModel<T> {
	Iterable<T> result;
	CursorMetaData metaData;

	public CursorResultViewModel(Iterable<T> result, String cursorProp, int limit, long totalItemCount)
    {
        this.result = result;
        this.metaData = new CursorMetaData(result, cursorProp, limit, totalItemCount);
    }

	public CursorResultViewModel(Page<T> result, String cursorProp)
    {
        this.result = result.getContent();
        this.metaData = new CursorMetaData(result, cursorProp);
    }

	public CursorResultViewModel(Page<T> result, LimitSortBindingModel lsBM)
    {
		this(result, lsBM.getSort());
    }
	
	public CursorResultViewModel(Iterable<T> result, LimitSortBindingModel lsBM, long totalItemCount)
    {
		this(result, lsBM.getSort(), lsBM.getLimit(), totalItemCount);
    }
	
	public CursorResultViewModel(Iterable<T> result, CursorMetaData metaData)
    {
        this.result = result;
        this.metaData = metaData;
    }
}
