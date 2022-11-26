package com.pcz.simple.spring.framework.beans.factory.support;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 基于 JDK 的实例化策略
 *
 * @author picongzhi
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> constructor, Object[] args)
            throws BeansException {
        if (beanDefinition == null || beanDefinition.getBeanClass() == null) {
            throw new BeansException("Failed to instantiate [" + beanName + "] because bean class is null");
        }

        Class<?> beanClass = beanDefinition.getBeanClass();
        try {
            if (constructor != null) {
                return beanClass.getDeclaredConstructor(constructor.getParameterTypes()).newInstance(args);
            } else {
                return beanClass.getDeclaredConstructor().newInstance();
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            throw new BeansException("Failed to instantiate [" + beanClass.getName() + "]", e);
        }
    }
}
