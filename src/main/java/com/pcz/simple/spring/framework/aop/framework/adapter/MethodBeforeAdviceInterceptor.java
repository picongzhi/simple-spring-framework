package com.pcz.simple.spring.framework.aop.framework.adapter;

import com.pcz.simple.spring.framework.aop.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 方法前置通知拦截器
 *
 * @author picongzhi
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {
    /**
     * 前置通知
     */
    private MethodBeforeAdvice beforeAdvice;

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice beforeAdvice) {
        this.beforeAdvice = beforeAdvice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        // 前置通知
        this.beforeAdvice.before(methodInvocation.getMethod(),
                methodInvocation.getArguments(), methodInvocation.getThis());

        // 执行目标方法
        return methodInvocation.proceed();
    }
}
