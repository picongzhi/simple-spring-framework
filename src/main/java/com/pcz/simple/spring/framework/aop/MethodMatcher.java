package com.pcz.simple.spring.framework.aop;

import java.lang.reflect.Method;

/**
 * 方法是否匹配
 *
 * @author picongzhi
 */
public interface MethodMatcher {
    /**
     * 方法是否匹配
     *
     * @param method      方法
     * @param targetClass 目标类
     * @return 是否匹配
     */
    boolean matches(Method method, Class<?> targetClass);
}
