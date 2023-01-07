package com.pcz.simple.spring.framework.aop.framework.adapter;

import com.pcz.simple.spring.framework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.pcz.simple.spring.framework.aop.aspectj.HelloService;
import com.pcz.simple.spring.framework.aop.aspectj.HelloServiceImpl;
import com.pcz.simple.spring.framework.aop.aspectj.LogBeforeAdvice;
import com.pcz.simple.spring.framework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import com.pcz.simple.spring.framework.beans.PropertyValue;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import com.pcz.simple.spring.framework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultAdvisorAutoProxyCreatorTest {
    private DefaultAdvisorAutoProxyCreator creator;

    private DefaultListableBeanFactory beanFactory;

    @BeforeEach
    public void setUp() {
        String expression = "execution(* com.pcz.simple.spring.framework.aop.aspectj.HelloService.*(..))";
        MethodInterceptor methodInterceptor = new MethodBeforeAdviceInterceptor(new LogBeforeAdvice());

        BeanDefinition advisorBeanDefinition = new BeanDefinition(AspectJExpressionPointcutAdvisor.class);
        advisorBeanDefinition.getPropertyValues()
                .addPropertyValue(new PropertyValue("expression", expression));
        advisorBeanDefinition.getPropertyValues()
                .addPropertyValue(new PropertyValue("advice", methodInterceptor));

        beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("logBeforeAdvisor", advisorBeanDefinition);

        creator = new DefaultAdvisorAutoProxyCreator();
        creator.setBeanFactory(beanFactory);
    }

    @Test
    public void should_post_process_before_instantisation() {
        Class<?> cls = HelloServiceImpl.class;
        String beanName = "helloService";
        Object proxy = creator.postProcessBeforeInstantiation(cls, beanName);
        Assertions.assertThat(proxy).isInstanceOf(HelloService.class);

        HelloService helloService = (HelloService) proxy;
        Assertions.assertThat(helloService.hello()).isNotNull();
    }
}
