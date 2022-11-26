package com.pcz.simple.spring.framework.beans.factory.config;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.BeanFactory;

/**
 * 可注入的 BeanFactory
 *
 * @author picongzhi
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    /**
     * 在 bean 初始化前应用 BeanPostProcessor
     *
     * @param existingBean 现有的 bean
     * @param beanName     bean name
     * @return 处理之后的 bean
     * @throws BeansException bean 异常
     * @see BeanPostProcessor
     */
    Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 在 bean 初始化后应用 BeanPostProcessor
     *
     * @param existingBean 现有的 bean
     * @param beanName     bean name
     * @return 处理之后的 bean
     * @throws BeansException bean 异常
     * @see BeanPostProcessor
     */
    Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
