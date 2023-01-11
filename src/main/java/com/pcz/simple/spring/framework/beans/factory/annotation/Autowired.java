package com.pcz.simple.spring.framework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * 自动注入
 *
 * @author picongzhi
 */
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
}
