package com.pcz.simple.spring.framework.beans.factory.config;

import com.pcz.simple.spring.framework.beans.PropertyValues;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class BeanDefinitionTest {
    @Test
    public void should_instantiation() {
        Class<?> beanClass = String.class;
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);
        Assertions.assertThat(beanDefinition).isNotNull();

        beanDefinition = new BeanDefinition(beanClass, null);
        Assertions.assertThat(beanDefinition).isNotNull();

        PropertyValues propertyValues = new PropertyValues();
        beanDefinition = new BeanDefinition(beanClass, propertyValues);
        Assertions.assertThat(beanDefinition).isNotNull();

        System.out.println(beanDefinition);
    }

    @Test
    public void should_get_bean_class() {
        Class<?> beanClass = String.class;
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);

        Class<?> result = beanDefinition.getBeanClass();
        Assertions.assertThat(result).isEqualTo(beanClass);
    }

    @Test
    public void should_set_bean_class() {
        Class<?> beanClass = String.class;
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);

        beanDefinition.setBeanClass(Integer.class);
        Assertions.assertThat(beanDefinition.getBeanClass()).isEqualTo(Integer.class);
    }

    @Test
    public void should_get_property_values() {
        Class<?> beanClass = String.class;
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);

        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        Assertions.assertThat(propertyValues).isNotNull();
    }

    @Test
    public void should_set_property_values() {
        Class<?> beanClass = String.class;
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);

        PropertyValues propertyValues = new PropertyValues();
        beanDefinition.setPropertyValues(propertyValues);

        PropertyValues result = beanDefinition.getPropertyValues();
        Assertions.assertThat(result).isEqualTo(propertyValues);
    }

    @Test
    public void should_get_init_method_name() {
        Class<?> beanClass = String.class;
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);

        String initMethodName = beanDefinition.getInitMethodName();
        Assertions.assertThat(initMethodName).isNull();
    }

    @Test
    public void should_set_init_method_name() {
        Class<?> beanClass = String.class;
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);

        String initMethodName = "init";
        beanDefinition.setInitMethodName(initMethodName);

        String result = beanDefinition.getInitMethodName();
        Assertions.assertThat(result).isEqualTo(initMethodName);
    }

    @Test
    public void should_get_destroy_method_name() {
        Class<?> beanClass = String.class;
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);

        String destroyMethodName = beanDefinition.getDestroyMethodName();
        Assertions.assertThat(destroyMethodName).isNull();
    }

    @Test
    public void should_set_destroy_method_name() {
        Class<?> beanClass = String.class;
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);

        String destroyMethodName = "destroy";
        beanDefinition.setDestroyMethodName(destroyMethodName);

        String result = beanDefinition.getDestroyMethodName();
        Assertions.assertThat(result).isEqualTo(destroyMethodName);
    }

    @Test
    public void should_set_scope() {
        Class<?> beanClass = String.class;
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);

        String scope = "singleton";
        beanDefinition.setScope(scope);
    }

    @Test
    public void should_be_singleton() {
        Class<?> beanClass = String.class;
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);

        String scope = "singleton";
        beanDefinition.setScope(scope);
        Assertions.assertThat(beanDefinition.isSingleton()).isTrue();
        Assertions.assertThat(beanDefinition.isPrototype()).isFalse();
    }

    @Test
    public void should_be_prototype() {
        Class<?> beanClass = String.class;
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);

        String scope = "prototype";
        beanDefinition.setScope(scope);
        Assertions.assertThat(beanDefinition.isPrototype()).isTrue();
        Assertions.assertThat(beanDefinition.isSingleton()).isFalse();
    }
}
