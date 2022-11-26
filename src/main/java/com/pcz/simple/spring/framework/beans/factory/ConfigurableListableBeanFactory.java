package com.pcz.simple.spring.framework.beans.factory;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.config.AutowireCapableBeanFactory;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import com.pcz.simple.spring.framework.beans.factory.config.ConfigurableBeanFactory;

/**
 * 可罗列的、可配置的 BeanFactory
 *
 * @author picongzhi
 */
public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
    /**
     * 根据 bean name 获取 BeanDefinition
     *
     * @param beanName bean name
     * @return BeanDefinition
     * @throws BeansException bean 异常
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 提前实例化单例
     *
     * @throws BeansException Beans 异常
     */
    void preInstantiateSingletons() throws BeansException;
}
