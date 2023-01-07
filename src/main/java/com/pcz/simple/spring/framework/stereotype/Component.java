package com.pcz.simple.spring.framework.stereotype;

import java.lang.annotation.*;

/**
 * 组件
 *
 * @author picongzhi
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    /**
     * 组件名称，默认空字符串
     *
     * @return 组件名称
     */
    String value() default "";
}
