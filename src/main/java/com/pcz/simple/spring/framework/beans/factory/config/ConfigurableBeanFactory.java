package com.pcz.simple.spring.framework.beans.factory.config;

import com.pcz.simple.spring.framework.beans.factory.HierarchicalBeanFactory;
import com.pcz.simple.spring.framework.util.StringValueResolver;

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

    /**
     * 添加嵌套的值解析器
     *
     * @param resolver 值解析器
     */
    void addEmbeddedValueResolver(StringValueResolver resolver);

    /**
     * 嵌套解析值
     *
     * @param value 值
     * @return 解析后的值
     */
    String resolveEmbeddedValue(String value);
}
