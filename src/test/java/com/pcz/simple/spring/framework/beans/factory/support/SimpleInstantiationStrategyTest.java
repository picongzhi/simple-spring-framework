package com.pcz.simple.spring.framework.beans.factory.support;

import cn.hutool.core.util.RandomUtil;
import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

public class SimpleInstantiationStrategyTest {
    private SimpleInstantiationStrategy simpleInstantiationStrategy;

    @BeforeEach
    public void setUp() {
        simpleInstantiationStrategy = new SimpleInstantiationStrategy();
    }

    @Test
    public void should_instantiate() throws NoSuchMethodException {
        String beanName = "foo";
        BeanDefinition beanDefinition = new BeanDefinition(Foo.class);
        Constructor<?> constructor = beanDefinition.getBeanClass().getConstructor(String.class);
        Object[] args = new Object[]{RandomUtil.randomString(10)};

        Object result = simpleInstantiationStrategy.instantiate(beanDefinition, beanName, constructor, args);
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void should_instantiate_when_constructor_is_null_and_default_constructor_exists() {
        String beanName = "foo";
        BeanDefinition beanDefinition = new BeanDefinition(Foo.class);

        Object result = simpleInstantiationStrategy.instantiate(beanDefinition, beanName, null, null);
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void should_throw_beans_exception_to_instantiate_when_args_not_match() throws NoSuchMethodException {
        String beanName = "foo";
        BeanDefinition beanDefinition = new BeanDefinition(Foo.class);
        Constructor<?> constructor = beanDefinition.getBeanClass().getConstructor(String.class);
        Object[] args = new Object[]{RandomUtil.randomString(10), RandomUtil.randomString(10)};

        Assertions.assertThatExceptionOfType(BeansException.class)
                .isThrownBy(() -> {
                    simpleInstantiationStrategy.instantiate(beanDefinition, beanName, constructor, args);
                });
    }

    @Test
    public void should_throw_beans_exception_to_instantiate_when_default_constructor_is_private() {
        String beanName = "bar";
        BeanDefinition beanDefinition = new BeanDefinition(Bar.class);

        Assertions.assertThatExceptionOfType(BeansException.class)
                .isThrownBy(() -> {
                    simpleInstantiationStrategy.instantiate(beanDefinition, beanName, null, null);
                });
    }

    @Test
    public void should_throw_beans_exception_to_instantiate_when_bean_definition_is_null() {
        String beanName = "foo";
        Assertions.assertThatExceptionOfType(BeansException.class)
                .isThrownBy(() -> {
                    simpleInstantiationStrategy.instantiate(null, beanName, null, null);
                });
    }

    @Test
    public void should_throw_beans_exception_to_instantiate_when_bean_class_is_null() {
        String beanName = "foo";
        BeanDefinition beanDefinition = new BeanDefinition(null);
        Assertions.assertThatExceptionOfType(BeansException.class)
                .isThrownBy(() -> {
                    simpleInstantiationStrategy.instantiate(null, beanName, null, null);
                });
    }

    private static class Foo {
        private String bar;

        public Foo() {
        }

        public Foo(String bar) {
            this.bar = bar;
        }
    }

    private static class Bar {
        private Bar() {
        }
    }
}
