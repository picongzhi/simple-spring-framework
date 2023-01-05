package com.pcz.simple.spring.framework.aop;

import java.lang.reflect.Method;

/**
 * 方法前置通知
 *
 * @author picongzhi
 */
public interface MethodBeforeAdvice extends BeforeAdvice {
    /**
     * 前置通知
     *
     * @param method 目标方法
     * @param args   目标方法参数
     * @param target 目标对象
     * @throws Throwable 异常
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}
