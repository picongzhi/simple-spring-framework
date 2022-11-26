package com.pcz.simple.spring.framework.beans.factory.support;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 实例化策略
 *
 * @author picongzhi
 */
public interface InstantiationStrategy {
    /**
     * 实例化
     *
     * @param beanDefinition BeanDefinition
     * @param beanName       bean name
     * @param constructor    构造方法
     * @param args           构造方法参数
     * @return 实例化对象
     * @throws BeansException bean 异常
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> constructor, Object[] args)
            throws BeansException;
}
