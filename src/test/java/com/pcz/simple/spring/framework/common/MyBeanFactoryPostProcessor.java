package com.pcz.simple.spring.framework.common;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.PropertyValue;
import com.pcz.simple.spring.framework.beans.PropertyValues;
import com.pcz.simple.spring.framework.beans.factory.ConfigurableListableBeanFactory;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import com.pcz.simple.spring.framework.beans.factory.config.BeanFactoryPostProcessor;

/**
 * @author picongzhi
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("id", "2"));
    }
}
