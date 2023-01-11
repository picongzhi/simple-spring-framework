package com.pcz.simple.spring.framework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * 限定符
 *
 * @author picongzhi
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Qualifier {
    /**
     * 限定符
     *
     * @return 限定符
     */
    String value() default "";
}
