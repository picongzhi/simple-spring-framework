package com.pcz.simple.spring.framework.beans.factory.config;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.ConfigurableListableBeanFactory;

/**
 * BeanFactory PostProcessor
 * {@link com.pcz.simple.spring.framework.beans.factory.BeanFactory} 后置处理器
 *
 * @author picongzhi
 */
public interface BeanFactoryPostProcessor {
    /**
     * 在加载完 {@link BeanDefinition} 之后，在实例化之前，提供修改 {@link BeanDefinition} 的机制
     *
     * @param beanFactory ConfigurableListableBeanFactory
     * @throws BeansException Bean 异常
     * @see ConfigurableListableBeanFactory
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
