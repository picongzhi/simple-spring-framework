package com.pcz.simple.spring.framework.beans.factory.support;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.FactoryBean;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FactoryBeanRegistrySupportTest {
    private FactoryBeanRegistrySupport support;

    @BeforeEach
    public void setUp() {
        support = new FactoryBeanRegistrySupport();
    }

    @Test
    public void should_get_cached_object_for_factory_bean() {
        String beanName = "name";
        FactoryBean<Object> factoryBean = new FactoryBean<Object>() {
            @Override
            public Object getObject() throws Exception {
                return new Object();
            }

            @Override
            public Class<?> getObjectType() {
                return Object.class;
            }

            @Override
            public boolean isSingleton() {
                return true;
            }
        };

        Object bean = support.getObjectFromFactoryBean(factoryBean, beanName);
        Object result = support.getCachedObjectForFactoryBean(beanName);
        Assertions.assertThat(result).isEqualTo(bean);
    }

    @Test
    public void should_get_object_from_factory_bean() {
        String beanName = "name";
        FactoryBean<Object> factoryBean = new FactoryBean<Object>() {
            @Override
            public Object getObject() throws Exception {
                return new Object();
            }

            @Override
            public Class<?> getObjectType() {
                return Object.class;
            }

            @Override
            public boolean isSingleton() {
                return true;
            }
        };

        Object bean = support.getObjectFromFactoryBean(factoryBean, beanName);
        Assertions.assertThat(bean).isNotNull();
    }

    @Test
    public void should_get_object_from_factory_bean_when_scope_is_prototype() {
        String beanName = "name";
        FactoryBean<Object> factoryBean = new FactoryBean<Object>() {
            @Override
            public Object getObject() throws Exception {
                return new Object();
            }

            @Override
            public Class<?> getObjectType() {
                return Object.class;
            }

            @Override
            public boolean isSingleton() {
                return false;
            }
        };

        Object bean = support.getObjectFromFactoryBean(factoryBean, beanName);
        Assertions.assertThat(bean).isNotNull();
    }

    @Test
    public void should_throw_beans_exception_to_get_object_from_factory_bean_when_factory_bean_get_object_throws_exception() {
        String beanName = "name";
        FactoryBean<Object> factoryBean = new FactoryBean<Object>() {
            @Override
            public Object getObject() throws Exception {
                throw new Exception("获取对象失败");
            }

            @Override
            public Class<?> getObjectType() {
                return Object.class;
            }

            @Override
            public boolean isSingleton() {
                return false;
            }
        };

        Assertions.assertThatExceptionOfType(BeansException.class)
                .isThrownBy(() -> support.getObjectFromFactoryBean(factoryBean, beanName));
    }
}
