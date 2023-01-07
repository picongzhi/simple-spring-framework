package com.pcz.simple.spring.framework.aop.framework;

import com.pcz.simple.spring.framework.aop.AdvisedSupport;
import com.pcz.simple.spring.framework.aop.TargetSource;
import com.pcz.simple.spring.framework.aop.aspectj.AspectJExpressionPointcut;
import com.pcz.simple.spring.framework.aop.aspectj.HelloService;
import com.pcz.simple.spring.framework.aop.aspectj.HelloServiceImpl;
import com.pcz.simple.spring.framework.aop.aspectj.LogBeforeAdvice;
import com.pcz.simple.spring.framework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProxyFactoryTest {
    private ProxyFactory proxyFactory;

    @Test
    public void should_get_proxy() {
        // 代理目标
        TargetSource targetSource = new TargetSource(new HelloServiceImpl());

        // 方法切面
        MethodInterceptor methodInterceptor = new MethodBeforeAdviceInterceptor(new LogBeforeAdvice());

        // 切入点
        String expression = "execution(* com.pcz.simple.spring.framework.aop.aspectj.HelloService.*(..))";
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut(expression);

        // 构造代理
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodInterceptor(methodInterceptor);
        advisedSupport.setMethodMatcher(pointcut.getMethodMatcher());
        advisedSupport.setProxyTargetClass(false);

        proxyFactory = new ProxyFactory(advisedSupport);
        // 获取代理对象
        HelloService helloService = (HelloService) proxyFactory.getProxy();
        Assertions.assertThat(helloService).isNotNull();

        String hello = helloService.hello();
        Assertions.assertThat(hello).isNotNull();
    }
}
