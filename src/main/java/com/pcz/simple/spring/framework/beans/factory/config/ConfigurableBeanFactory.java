package com.pcz.simple.spring.framework.beans.factory.config;

import com.pcz.simple.spring.framework.beans.factory.HierarchicalBeanFactory;

/**
 * 可配置的 BeanFactory
 *
 * @author picongzhi
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    /**
     * 单例作用域
     */
    String SCOPE_SINGLETON = "singleton";

    /**
     * 原型作用域
     */
    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 添加 BeanPostProcessor
     *
     * @param beanPostProcessor Bean PostProcessor
     * @see BeanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁所有单例
     */
    void destroySingletons();
}
