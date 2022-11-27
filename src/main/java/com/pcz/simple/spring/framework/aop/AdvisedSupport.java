package com.pcz.simple.spring.framework.aop;


import org.aopalliance.intercept.MethodInterceptor;

/**
 * 通知辅助类
 * 集成了被代理的目标对象、方法拦截器和方法匹配器
 *
 * @author picongzhi
 */
public class AdvisedSupport {
    /**
     * 被代理的目标
     */
    private TargetSource targetSource;

    /**
     * 方法拦截器
     */
    private MethodInterceptor methodInterceptor;

    /**
     * 方法匹配器
     */
    private MethodMatcher methodMatcher;

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
