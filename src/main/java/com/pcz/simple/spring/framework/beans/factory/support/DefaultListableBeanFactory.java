package com.pcz.simple.spring.framework.beans.factory.support;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.ConfigurableListableBeanFactory;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认的 Bean 工厂
 *
 * @author picongzhi
 */
public class DefaultListableBeanFactory
        extends AbstractAutowireCapableBeanFactory
        implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    /**
     * bean name -> BeanDefinition 映射
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }

        return beanDefinition;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        Map<String, T> beansOfType = new HashMap<>();
        this.beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class<?> beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                beansOfType.put(beanName, getBean(beanName, type));
            }
        });

        return beansOfType;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
        this.beanDefinitionMap.keySet().forEach(this::getBean);
    }
}
