package com.pcz.simple.spring.framework.beans.factory.config;

/**
 * 单例 bean 注册中心
 *
 * @author picongzhi
 */
public interface SingletonBeanRegistry {
    /**
     * 获取 bean 单例
     *
     * @param beanName bean name
     * @return bean 单例
     */
    Object getSingleton(String beanName);

    /**
     * 注册 bean 单例
     *
     * @param beanName        bean name
     * @param singletonObject bean 单例
     */
    void registerSingleton(String beanName, Object singletonObject);
}
