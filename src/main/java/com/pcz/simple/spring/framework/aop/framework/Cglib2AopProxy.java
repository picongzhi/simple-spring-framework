package com.pcz.simple.spring.framework.aop.framework;

import com.pcz.simple.spring.framework.aop.AdvisedSupport;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib 代理
 *
 * @author picongzhi
 */
public class Cglib2AopProxy implements AopProxy {
    /**
     * 通知
     */
    private final AdvisedSupport advisedSupport;

    public Cglib2AopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.advisedSupport.getTargetSource().getTarget().getClass());
        enhancer.setInterfaces(this.advisedSupport.getTargetSource().getTargetClass());
        enhancer.setCallback(new DynamicAdvisedInterceptor(this.advisedSupport));

        return enhancer.create();
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor {
        private final AdvisedSupport advisedSupport;

        public DynamicAdvisedInterceptor(AdvisedSupport advisedSupport) {
            this.advisedSupport = advisedSupport;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            Object target = this.advisedSupport.getTargetSource().getTarget();
            Class<?> targetClass = target.getClass();

            CglibMethodInvocation methodInvocation = new CglibMethodInvocation(target, method, args, proxy);
            if (!this.advisedSupport.getMethodMatcher().matches(method, targetClass)) {
                return methodInvocation.proceed();
            }

            return this.advisedSupport.getMethodInterceptor().invoke(methodInvocation);
        }
    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy methodProxy) {
            super(target, method, arguments);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return this.methodProxy.invoke(target, arguments);
        }
    }
}
