package com.pcz.simple.spring.framework.context.annotation;

import java.lang.annotation.*;

/**
 * 作用域
 *
 * @author picongzhi
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {
    /**
     * 作用域，默认是单例
     *
     * @return 作用域
     */
    String value() default "singleton";
}
