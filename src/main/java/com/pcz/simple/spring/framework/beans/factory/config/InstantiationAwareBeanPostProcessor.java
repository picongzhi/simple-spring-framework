package com.pcz.simple.spring.framework.beans.factory.config;

import com.pcz.simple.spring.framework.beans.BeansException;

/**
 * 实例化 BeanPostProsessor
 *
 * @author picongzhi
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /**
     * 在 Bean 实例化之前执行
     *
     * @param beanClass Bean Class 实例
     * @param beanName  Bean 名称
     * @return Bean 实例
     * @throws BeansException Bean 异常
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
}
