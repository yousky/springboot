package org.bwyou.springboot.model.viewmodel;

import lombok.Data;

@Data
public class CursorResultViewModel<T> {
	Iterable<T> result;
	CursorMetaData metaData;

	public CursorResultViewModel(Iterable<T> result, String cursorProp)
    {
        this.result = result;
        this.metaData = new CursorMetaData(result, cursorProp);
    }
	
	public CursorResultViewModel(Iterable<T> result, CursorMetaData metaData)
    {
        this.result = result;
        this.metaData = metaData;
    }
}
