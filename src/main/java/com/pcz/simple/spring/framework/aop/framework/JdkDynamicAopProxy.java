package com.pcz.simple.spring.framework.aop.framework;

import com.pcz.simple.spring.framework.aop.AdvisedSupport;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK 动态代理
 *
 * @author picongzhi
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {
    /**
     * 通知
     */
    private final AdvisedSupport advisedSupport;

    public JdkDynamicAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                this.advisedSupport.getTargetSource().getTargetClass(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object target = this.advisedSupport.getTargetSource().getTarget();
        Class<?> targetClass = target.getClass();
        if (!this.advisedSupport.getMethodMatcher().matches(method, targetClass)) {
            return method.invoke(target, args);
        }

        MethodInterceptor methodInterceptor = this.advisedSupport.getMethodInterceptor();
        MethodInvocation methodInvocation = new ReflectiveMethodInvocation(target, method, args);

        return methodInterceptor.invoke(methodInvocation);
    }
}
