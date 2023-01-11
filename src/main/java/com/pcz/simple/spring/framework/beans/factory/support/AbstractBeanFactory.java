package com.pcz.simple.spring.framework.beans.factory.support;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.FactoryBean;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import com.pcz.simple.spring.framework.beans.factory.config.BeanPostProcessor;
import com.pcz.simple.spring.framework.beans.factory.config.ConfigurableBeanFactory;
import com.pcz.simple.spring.framework.util.ClassUtils;
import com.pcz.simple.spring.framework.util.StringValueResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象的 bean 工厂
 *
 * @author picongzhi
 */
public abstract class AbstractBeanFactory
        extends FactoryBeanRegistrySupport
        implements ConfigurableBeanFactory {
    /**
     * BeanPostProcessors
     */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    /**
     * bean ClassLoader
     */
    private final ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    /**
     * 嵌套的值解析器
     */
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    @Override

    public Object getBean(String beanName) throws BeansException {
        return doGetBean(beanName, null);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return doGetBean(beanName, args);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        return (T) getBean(beanName);
    }

    /**
     * 获取 bean 实例
     *
     * @param beanName bean name
     * @param args     构造函数参数
     * @return bean 实例
     * @throws BeansException bean 异常
     */
    protected <T> T doGetBean(String beanName, Object[] args) throws BeansException {
        Object sharedInstance = getSingleton(beanName);
        if (sharedInstance != null) {
            return (T) getObjectForBeanInstance(sharedInstance, beanName);
        }

        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Object bean = createBean(beanName, beanDefinition, args);

        return (T) getObjectForBeanInstance(bean, beanName);
    }

    /**
     * 从 bean 实例中获取对象实例
     * 如果不是 {@link FactoryBean}，直接返回实例
     * 如果是 {@link FactoryBean}，先通过 {@link FactoryBeanRegistrySupport#getCachedObjectForFactoryBean(String)} 获取缓存的实例对象，
     * 缓存不存在则通过 {@link FactoryBeanRegistrySupport#getObjectFromFactoryBean(FactoryBean, String)} 获取对象实例
     *
     * @param beanInstance bean 实例
     * @param beanName     bean 名称
     * @return 对象实例
     */
    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        Object object = getCachedObjectForFactoryBean(beanName);
        if (object != null) {
            return object;
        }

        FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
        object = getObjectFromFactoryBean(factoryBean, beanName);

        return object;
    }

    /**
     * 获取 BeanDefinition
     *
     * @param beanName bean name
     * @return BeanDefinition
     * @throws BeansException bean 异常
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 创建 bean 实例
     *
     * @param beanName       bean name
     * @param beanDefinition BeanDefinition
     * @param args           构造函数参数
     * @return bean 实例
     * @throws BeansException bean 异常
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * 获取所有 BeanPostProcessor
     *
     * @return 所有 BeanPostProcessor
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    /**
     * 获取 bean ClassLoader
     *
     * @return ClassLoader
     */
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver resolver) {
        this.embeddedValueResolvers.add(resolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
            result = resolver.resolveStringValue(value);
        }

        return result;
    }
}
