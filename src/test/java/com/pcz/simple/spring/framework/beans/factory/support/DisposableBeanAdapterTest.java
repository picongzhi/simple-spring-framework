package com.pcz.simple.spring.framework.beans.factory.support;

import cn.hutool.core.util.RandomUtil;
import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.DisposableBean;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DisposableBeanAdapterTest {
    @Test
    public void should_destroy() throws Exception {
        String beanName = "demoBean";
        DemoBean bean = new DemoBean();
        BeanDefinition beanDefinition = new BeanDefinition(DemoBean.class);
        beanDefinition.setDestroyMethodName("doDestroy");

        DisposableBeanAdapter disposableBeanAdapter = new DisposableBeanAdapter(bean, beanName, beanDefinition);
        disposableBeanAdapter.destroy();
    }

    @Test
    public void should_throw_beans_exception_to_destroy_when_destroy_method_not_found() throws Exception {
        String beanName = "demoBean";
        DemoBean bean = new DemoBean();
        BeanDefinition beanDefinition = new BeanDefinition(DemoBean.class);
        beanDefinition.setDestroyMethodName(RandomUtil.randomString(10));

        DisposableBeanAdapter disposableBeanAdapter = new DisposableBeanAdapter(bean, beanName, beanDefinition);
        Assertions.assertThatExceptionOfType(BeansException.class)
                .isThrownBy(() -> disposableBeanAdapter.destroy());
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
