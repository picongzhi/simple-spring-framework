package com.pcz.simple.spring.framework.context;

import com.pcz.simple.spring.framework.beans.factory.support.BeanDefinitionRegistry;
import com.pcz.simple.spring.framework.beans.factory.support.DefaultListableBeanFactory;
import com.pcz.simple.spring.framework.context.annotation.ClassPathBeanDefinitionScanner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClassPathBeanDefinitionScannerTest {
    private ClassPathBeanDefinitionScanner scanner;

    private BeanDefinitionRegistry registry;

    @BeforeEach
    public void setUp() {
        registry = new DefaultListableBeanFactory();
        scanner = new ClassPathBeanDefinitionScanner(registry);
    }

    @Test
    public void should_do_scan() {
        String[] basePackages = new String[]{"com.pcz.simple.spring.framework.context.annotation"};
        scanner.doScan(basePackages);

        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        Assertions.assertThat(beanDefinitionNames).hasSize(1);
    }
}
