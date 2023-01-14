package com.pcz.simple.spring.framework.beans.factory.config;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.PropertyValues;

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

    /**
     * 在 Bean 实例化之后执行
     *
     * @param bean     Bean 实例
     * @param beanName Bean 名称
     * @return 是否继续后续属性填充
     * @throws BeansException Bean 异常
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    /**
     * 在 Bean 实例化之后，设置属性之前执行
     *
     * @param propertyValues 属性值
     * @param bean           Bean 实例
     * @param beanName       Bean 名称
     * @return 处理之后的属性值
     * @throws BeansException Bean 异常
     */
    PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeansException;
}
