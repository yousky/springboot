package org.bwyou.springboot.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BWRepository<TEntity, ID extends Serializable> extends JpaRepository<TEntity, ID>, JpaSpecificationExecutor<TEntity>{

}
