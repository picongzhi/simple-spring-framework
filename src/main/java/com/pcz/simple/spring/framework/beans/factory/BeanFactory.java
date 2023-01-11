package com.pcz.simple.spring.framework.beans.factory;

import com.pcz.simple.spring.framework.beans.BeansException;

/**
 * Bean 工厂
 *
 * @author picongzhi
 */
public interface BeanFactory {
    /**
     * 根据 bean name 获取 bean 实例
     *
     * @param beanName bean name
     * @return bean 实例
     * @throws BeansException bean 异常
     */
    Object getBean(String beanName) throws BeansException;

    /**
     * 根据 bean name 和构造函数参数获取 bean 实例
     *
     * @param beanName bean name
     * @param args     构造函数参数
     * @return bean 实例
     * @throws BeansException bean 异常
     */
    Object getBean(String beanName, Object... args) throws BeansException;

    /**
     * 根据 bean name 和 Class 实例获取 bean 实例
     *
     * @param beanName     bean name
     * @param requiredType Class 实例
     * @param <T>          泛型
     * @return bean 实例
     * @throws BeansException bean 异常
     */
    <T> T getBean(String beanName, Class<T> requiredType) throws BeansException;

    /**
     * 根据 Class 实例获取 bean 实例
     *
     * @param requiredType Class 实例
     * @param <T>          泛型
     * @return bean 实例
     * @throws BeansException bean 异常
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;
}
