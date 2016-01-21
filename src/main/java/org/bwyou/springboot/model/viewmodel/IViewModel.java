package org.bwyou.springboot.model.viewmodel;

public interface IViewModel<TEntityVM, TEntitySrc> {
	TEntityVM LoadModel(TEntitySrc baseModel, boolean recursive, String sort);
	
}
