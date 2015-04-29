package org.bwyou.springboot.model.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Updatable Field Check
 * 
 * @author yousky
 *
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Updatable {

}
