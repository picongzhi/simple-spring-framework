package com.pcz.simple.spring.framework.beans.factory.support;

import com.pcz.simple.spring.framework.core.io.DefaultResourceLoader;
import com.pcz.simple.spring.framework.core.io.ResourceLoader;

/**
 * 抽象的 BeanDefinition Reader
 *
 * @author picongzhi
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    /**
     * BeanDefinition 注册器
     */
    private final BeanDefinitionRegistry registry;

    /**
     * 资源加载器
     */
    private final ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return this.registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }
}
