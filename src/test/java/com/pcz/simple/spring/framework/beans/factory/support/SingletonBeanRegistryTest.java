package com.pcz.simple.spring.framework.beans.factory.support;

import cn.hutool.core.util.RandomUtil;
import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.DisposableBean;
import com.pcz.simple.spring.framework.beans.factory.ObjectFactory;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SingletonBeanRegistryTest {
    private DefaultSingletonBeanRegistry registry;

    @BeforeEach
    public void setUp() {
        registry = new DefaultSingletonBeanRegistry();
    }

    @Test
    public void should_get_singleton() {
        String beanName = "name";
        Object bean = new Object();
        registry.registerSingleton(beanName, bean);

        Object result = registry.getSingleton(beanName);
        Assertions.assertThat(result).isEqualTo(bean);
    }

    @Test
    public void should_get_early_singleton() {
        String beanName = "name";
        ObjectFactory<Object> objectFactory = new ObjectFactory<Object>() {
            @Override
            public Object getObject() throws BeansException {
                return new Object();
            }
        };
        registry.addSingletonFactory(beanName, objectFactory);

        // 从工厂对象获取单例，触发二级缓存写入
        Object result = registry.getSingleton(beanName);
        Assertions.assertThat(result).isNotNull();

        // 从二级缓存获取半成品 bean
        result = registry.getSingleton(beanName);
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void should_return_null_to_get_singleton() {
        String beanName = "name";
        Object result = registry.getSingleton(beanName);
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void should_register_singleton() {
        String beanName = "name";
        Object bean = new Object();
        registry.registerSingleton(beanName, bean);
    }

    @Test
    public void should_add_singleton_factory() {
        String beanName = "name";
        ObjectFactory<Object> objectFactory = new ObjectFactory<Object>() {
            @Override
            public Object getObject() throws BeansException {
                return new Object();
            }
        };
        registry.addSingletonFactory(beanName, objectFactory);
    }

    @Test
    public void should_register_disposable_bean() {
        String beanName = "name";
        Object bean = new Object();
        BeanDefinition beanDefinition = new BeanDefinition(Object.class);
        DisposableBean disposableBean = new DisposableBeanAdapter(bean, beanName, beanDefinition);
        registry.registerDisposableBean(beanName, disposableBean);
    }

    @Test
    public void should_destroy_singletons() {
        String beanName = "demoBean";
        DemoBean bean = new DemoBean();
        BeanDefinition beanDefinition = new BeanDefinition(DemoBean.class);
        registry.registerDisposableBean(beanName,
                new DisposableBeanAdapter(bean, beanName, beanDefinition));

        registry.destroySingletons();
    }

    @Test
    public void should_throw_beans_exception_to_destroy_singletons_when_destroy_method_not_found() {
        String beanName = "demoBean";
        DemoBean bean = new DemoBean();
        BeanDefinition beanDefinition = new BeanDefinition(DemoBean.class);
        beanDefinition.setDestroyMethodName(RandomUtil.randomString(10));
        registry.registerDisposableBean(beanName,
                new DisposableBeanAdapter(bean, beanName, beanDefinition));

        Assertions.assertThatExceptionOfType(BeansException.class)
                .isThrownBy(() -> registry.destroySingletons());
    }

    private static class DemoBean implements DisposableBean {
        @Override
        public void destroy() throws Exception {
            System.out.println("destroy");
        }

        public void doDestroy() {
            System.out.println("doDestroy");
        }
    }
}
