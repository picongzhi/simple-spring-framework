package com.pcz.simple.spring.framework.beans.factory;

import com.pcz.simple.spring.framework.beans.PropertyValue;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import com.pcz.simple.spring.framework.beans.factory.support.DefaultListableBeanFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PropertyPlaceholderConfigurerTest {
    private PropertyPlaceholderConfigurer propertyPlaceholderConfigurer;

    private DefaultListableBeanFactory beanFactory;

    @BeforeEach
    public void setUp() {
        beanFactory = new DefaultListableBeanFactory();
        propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
    }

    @Test
    public void should_post_process_bean_factory() {
        BeanDefinition beanDefinition = new BeanDefinition(DemoBean.class);
        beanDefinition.getPropertyValues()
                .addPropertyValue(new PropertyValue("name", "${name}"));

        beanFactory.registerBeanDefinition("demoBean", beanDefinition);

        String location = "classpath:demo-bean.properties";
        propertyPlaceholderConfigurer.setLocaltion(location);
        propertyPlaceholderConfigurer.postProcessBeanFactory(beanFactory);
    }

    private static class DemoBean {
        private String name;
    }
}
