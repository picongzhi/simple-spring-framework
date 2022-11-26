package com.pcz.simple.spring.framework.beans.factory.support;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.core.io.Resource;
import com.pcz.simple.spring.framework.core.io.ResourceLoader;

/**
 * BeanDefinition Reader
 *
 * @author picongzhi
 * @see com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition
 */
public interface BeanDefinitionReader {
    /**
     * 获取 BeanDefinition 注册器
     *
     * @return BeanDefinition 注册器
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 获取资源加载器
     *
     * @return 资源加载器
     */
    ResourceLoader getResourceLoader();

    /**
     * 加载 BeanDefinition
     *
     * @param resource 资源
     * @throws BeansException Bean 异常
     */
    void loadBeanDefinitions(Resource resource) throws BeansException;

    /**
     * 加载 BeanDefinition
     *
     * @param resources 资源
     * @throws BeansException Bean 异常
     */
    void loadBeanDefinitions(Resource... resources) throws BeansException;

    /**
     * 加载 BeanDefinition
     *
     * @param location 路径
     * @throws BeansException Bean 异常
     */
    void loadBeanDefinitions(String location) throws BeansException;

    /**
     * 加载 BeanDefinition
     *
     * @param locations 路径
     * @throws BeansException Bean 异常
     */
    void loadBeanDefinitions(String... locations) throws BeansException;
}
