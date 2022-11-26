package com.pcz.simple.spring.framework.beans.factory.support;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

/**
 * 基于 Cglib 的实例化策略
 *
 * @author picongzhi
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> constructor, Object[] args)
            throws BeansException {
        if (beanDefinition == null || beanDefinition.getBeanClass() == null) {
            throw new BeansException("Failed to instantiate [" + beanName + "] because bean class is null");
        }

        Class<?> beanClass = beanDefinition.getBeanClass();
        
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanClass);
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });

        if (constructor == null) {
            return enhancer.create();
        }

        try {
            return enhancer.create(constructor.getParameterTypes(), args);
        } catch (IllegalArgumentException e) {
            throw new BeansException("Failed to instantiate [" + beanClass.getName() + "]", e);
        }
    }
}
