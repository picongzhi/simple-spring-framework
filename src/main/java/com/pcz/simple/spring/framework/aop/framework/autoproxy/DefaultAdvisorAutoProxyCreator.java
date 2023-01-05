package com.pcz.simple.spring.framework.aop.framework.autoproxy;

import com.pcz.simple.spring.framework.aop.*;
import com.pcz.simple.spring.framework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.pcz.simple.spring.framework.aop.framework.ProxyFactory;
import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.BeanFactory;
import com.pcz.simple.spring.framework.beans.factory.BeanFactoryAware;
import com.pcz.simple.spring.framework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.pcz.simple.spring.framework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;

/**
 * 默认的自动代理创造器
 *
 * @author picongzhi
 */
public class DefaultAdvisorAutoProxyCreator
        implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    /**
     * BeanFactory
     */
    private DefaultListableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (isInfrastructureClass(beanClass)) {
            return null;
        }

        Collection<AspectJExpressionPointcutAdvisor> advisors =
                beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class)
                        .values();
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            if (!classFilter.matches(beanClass)) {
                continue;
            }

            TargetSource targetSource = null;
            try {
                Object instance = beanClass.getDeclaredConstructor().newInstance();
                targetSource = new TargetSource(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }

            AdvisedSupport advisedSupport = new AdvisedSupport();
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);

            return new ProxyFactory(advisedSupport).getProxy();
        }

        return null;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass)
                || Pointcut.class.isAssignableFrom(beanClass);
    }
}
