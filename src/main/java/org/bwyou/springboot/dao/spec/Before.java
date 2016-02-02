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
public class Before<T, C extends Comparable<? super C>> extends PathSpecification<T> {

    private C value;
    protected Converter converter;

    public Before(String path, String... args) throws ParseException {
        this(path, args, null);
    }
    
    @SuppressWarnings("unchecked")
	public Before(String path, String[] args, String[] config) throws ParseException {
        super(path);
        if (args == null || args.length != 1) {
            throw new IllegalArgumentException("expected a single http-param, but was: " + args);
        }
        String str = args[0];
        this.value = (C) converter.convert(str, value.getClass());
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.lessThan(this.<C>path(root), value);
    }

}
