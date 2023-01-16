package com.pcz.simple.spring.framework.beans.factory.support;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.PropertyValue;
import com.pcz.simple.spring.framework.beans.PropertyValues;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import com.pcz.simple.spring.framework.beans.factory.config.BeanReference;
import com.pcz.simple.spring.framework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class DefaultListableBeanFactoryTest {
    private DefaultListableBeanFactory beanFactory;

    @BeforeEach
    public void setUp() {
        beanFactory = new DefaultListableBeanFactory();
    }

    @Test
    public void should_register_bean_definition() {
        String beanName = "name";
        BeanDefinition beanDefinition = new BeanDefinition(Object.class);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    @Test
    public void should_get_bean_definition() {
        String beanName = "name";
        BeanDefinition beanDefinition = new BeanDefinition(Object.class);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);

        BeanDefinition result = beanFactory.getBeanDefinition(beanName);
        Assertions.assertThat(result).isEqualTo(beanDefinition);
    }

    @Test
    public void should_throw_beans_exception_to_get_bean_definition_when_bean_definition_not_registered() {
        String beanName = "name";
        Assertions.assertThatExceptionOfType(BeansException.class)
                .isThrownBy(() -> beanFactory.getBeanDefinition(beanName));
    }

    @Test
    public void should_contains_bean_definition() {
        String beanName = "name";
        BeanDefinition beanDefinition = new BeanDefinition(Object.class);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);

        boolean contains = beanFactory.containsBeanDefinition(beanName);
        Assertions.assertThat(contains).isTrue();
    }

    @Test
    public void should_not_contains_bean_definition() {
        String beanName = "name";
        boolean contains = beanFactory.containsBeanDefinition(beanName);
        Assertions.assertThat(contains).isFalse();
    }

    @Test
    public void should_get_beans_of_type() {
        String beanName = "name";
        BeanDefinition beanDefinition = new BeanDefinition(String.class);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);

        Map<String, String> beansOfType = beanFactory.getBeansOfType(String.class);
        Assertions.assertThat(beansOfType).hasSize(1);
    }

    @Test
    public void should_get_bean_definition_names() {
        String beanName = "name";
        BeanDefinition beanDefinition = new BeanDefinition(String.class);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);

        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        Assertions.assertThat(beanDefinitionNames).hasSize(1);
    }

    @Test
    public void should_pre_instantiate_singletons() {
        String beanName = "name";
        BeanDefinition beanDefinition = new BeanDefinition(String.class);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);

        beanFactory.preInstantiateSingletons();
    }

    @Test
    public void should_get_bean() {
        String beanName = "name";
        BeanDefinition beanDefinition = new BeanDefinition(String.class);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);

        String result = beanFactory.getBean(String.class);
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void should_throw_bean_exception_to_get_bean_when_bean_definition_not_registered() {
        Assertions.assertThatExceptionOfType(BeansException.class)
                .isThrownBy(() -> beanFactory.getBean(String.class));
    }

    @Test
    public void should_create_bean() {
        String beanName = "name";
        BeanDefinition beanDefinition = new BeanDefinition(String.class);

        Object bean = beanFactory.createBean(beanName, beanDefinition, null);
        Assertions.assertThat(bean).isNotNull();
    }

    @Test
    public void should_return_proxy_object_to_create_bean_when_post_processors_before_instantiation() {
        beanFactory.addBeanPostProcessor(new InstantiationAwareBeanPostProcessor() {
            @Override
            public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
                // 实例化前返回代理对象
                return new Object();
            }

            @Override
            public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
                return false;
            }

            @Override
            public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeansException {
                return null;
            }

            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return null;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return null;
            }
        });

        String beanName = "name";
        BeanDefinition beanDefinition = new BeanDefinition(String.class);

        Object bean = beanFactory.createBean(beanName, beanDefinition, null);
        Assertions.assertThat(bean).isNotNull();
    }

    @Test
    public void should_create_bean_when_post_processors_after_instantiation_return_false() {
        beanFactory.addBeanPostProcessor(new InstantiationAwareBeanPostProcessor() {
            @Override
            public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
                return null;
            }

            @Override
            public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
                return false;
            }

            @Override
            public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeansException {
                return null;
            }

            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return null;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return null;
            }
        });

        String beanName = "name";
        BeanDefinition beanDefinition = new BeanDefinition(String.class);

        Object bean = beanFactory.createBean(beanName, beanDefinition, null);
        Assertions.assertThat(bean).isNotNull();
    }

    @Test
    public void should_throw_beans_exception_to_create_bean_when_bean_class_instantiation_failed() {
        String beanName = "demoBean";
        BeanDefinition beanDefinition = new BeanDefinition(DemoBean.class);

        Assertions.assertThatExceptionOfType(BeansException.class)
                .isThrownBy(() -> beanFactory.createBean(beanName, beanDefinition, null));
    }

    @Test
    public void should_create_bean_when_bean_has_loop_dependency() {
        BeanDefinition bBeanDefinition = new BeanDefinition(B.class);
        beanFactory.registerBeanDefinition("b", bBeanDefinition);

        BeanDefinition aBeanDefinition = new BeanDefinition(A.class);
        beanFactory.registerBeanDefinition("a", aBeanDefinition);

        // a 依赖 b
        PropertyValues aPropertyValues = new PropertyValues();
        aPropertyValues.addPropertyValue(new PropertyValue("b", new BeanReference("b")));
        aBeanDefinition.setPropertyValues(aPropertyValues);

        // b 依赖 a
        PropertyValues bPropertyValues = new PropertyValues();
        bPropertyValues.addPropertyValue(new PropertyValue("a", new BeanReference("a")));
        bBeanDefinition.setPropertyValues(bPropertyValues);

        Object bean = beanFactory.createBean("a", aBeanDefinition, null);
        Assertions.assertThat(bean).isNotNull();
    }

    private static class DemoBean {
    }

    public static class A {
        private B b;
    }

    public static class B {
        private A a;
    }
}
