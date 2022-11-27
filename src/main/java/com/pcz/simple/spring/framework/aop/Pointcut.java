package com.pcz.simple.spring.framework.aop;

/**
 * 切入点
 *
 * @author picongzhi
 */
public interface Pointcut {
    /**
     * 获取 ClassFilter
     *
     * @return ClassFilter
     */
    ClassFilter getClassFilter();

    /**
     * 获取 MethodMatcher
     *
     * @return MethodMatcher
     */
    MethodMatcher getMethodMatcher();
}
