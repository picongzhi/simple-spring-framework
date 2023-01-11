package com.pcz.simple.spring.framework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * 注入值
 *
 * @author picongzhi
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {
    /**
     * 值表达式
     * 比如："#{systemProperties.myProp}"
     *
     * @return 值表达式
     */
    String value();
}
