package org.bwyou.springboot.dao.spec;

import java.text.ParseException;

import net.kaczmarzyk.spring.data.jpa.domain.PathSpecification;
import net.kaczmarzyk.spring.data.jpa.utils.Converter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
/**
 * Filters with {@code path > value} where-clause.
 * 
 * @author BW You
 */
public class After<Model, C extends Comparable<? super C>> extends PathSpecification<Model> {

    private C value;
    protected Converter converter;

    public After(String path, String... args) throws ParseException {
        this(path, args, null);
    }
    
    @SuppressWarnings("unchecked")
	public After(String path, String[] args, String[] config) throws ParseException {
        super(path);
        if (args == null || args.length != 1) {
            throw new IllegalArgumentException("expected a single http-param, but was: " + args);
        }
        String str = args[0];
        this.value = (C) str;
    }

    @Override
    public Predicate toPredicate(Root<Model> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.greaterThan(this.<C>path(root), value);
    }

}
