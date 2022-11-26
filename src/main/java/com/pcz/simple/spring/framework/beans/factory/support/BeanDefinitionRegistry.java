package com.pcz.simple.spring.framework.beans.factory.support;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;

/**
 * BeanDefinition 注册中心
 *
 * @author picongzhi
 */
public interface BeanDefinitionRegistry {
    /**
     * 注册 BeanDefinition
     *
     * @param beanName       bean name
     * @param beanDefinition BeanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 根据 bean name 获取 BeanDefinition
     *
     * @param beanName bean name
     * @return BeanDefinition
     * @throws BeansException Bean 异常
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 判断是否包含 BeanDefinition
     *
     * @param beanName bean name
     * @return 是否包含 BeanDefinition
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 获取所有 BeanDefinition 的名称
     *
     * @return 所有 BeanDefinition 的名称
     */
    String[] getBeanDefinitionNames();
}
