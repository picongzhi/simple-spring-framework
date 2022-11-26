package com.pcz.simple.spring.framework.beans.factory;

/**
 * 工厂 bean
 *
 * @author picongzhi
 */
public interface FactoryBean<T> {
    /**
     * 获取 bane 实例
     *
     * @return bean 实例
     * @throws Exception 异常
     */
    T getObject() throws Exception;

    /**
     * 获取 bean Class 实例
     *
     * @return bean Class 实例
     */
    Class<?> getObjectType();

    /**
     * 是否是单例
     *
     * @return 是否是单例
     */
    boolean isSingleton();
}
