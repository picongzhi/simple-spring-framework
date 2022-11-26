package com.pcz.simple.spring.framework.beans.factory.config;

import com.pcz.simple.spring.framework.beans.BeansException;

/**
 * Bean PostProcessor
 * Bean 后置处理器
 *
 * @author picongzhi
 */
public interface BeanPostProcessor {
    /**
     * Bean 初始化之前处理
     *
     * @param bean     bean 实例
     * @param beanName bean name
     * @return 处理之后返回的 Bean 实例
     * @throws BeansException Bean 异常
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * Bean 初始化之后处理
     *
     * @param bean     bean 实例
     * @param beanName bean name
     * @return 处理之后返回的 Bean 实例
     * @throws BeansException Bean 异常
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
