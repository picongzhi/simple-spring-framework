package com.pcz.simple.spring.framework.beans.factory.xml;

import cn.hutool.core.util.RandomUtil;
import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.support.BeanDefinitionRegistry;
import com.pcz.simple.spring.framework.core.io.Resource;
import com.pcz.simple.spring.framework.core.io.ResourceLoader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public class XmlBeanDefinitionReaderTest {
    private XmlBeanDefinitionReader beanDefinitionReader;

    private BeanDefinitionRegistry beanDefinitionRegistry;

    private ResourceLoader resourceLoader;

    @BeforeEach
    public void setUp() {
        beanDefinitionRegistry = Mockito.mock(BeanDefinitionRegistry.class);
        resourceLoader = Mockito.mock(ResourceLoader.class);
        beanDefinitionReader = new XmlBeanDefinitionReader(beanDefinitionRegistry);
    }

    @Test
    public void should_instantiation_with_resource_loader() {
        beanDefinitionReader = new XmlBeanDefinitionReader(beanDefinitionRegistry, resourceLoader);
        Assertions.assertThat(beanDefinitionReader).isNotNull();
    }

    @Test
    public void should_load_bean_definitions_by_resource() {
        String location = "classpath:xml-bean-definition-reader.xml";
        ResourceLoader resourceLoader = beanDefinitionReader.getResourceLoader();
        Resource resource = resourceLoader.getResource(location);

        beanDefinitionReader.loadBeanDefinitions(resource);
    }

    @Test
    public void should_load_bean_definitions_by_resources() {
        String location = "classpath:xml-bean-definition-reader.xml";
        ResourceLoader resourceLoader = beanDefinitionReader.getResourceLoader();
        Resource resource = resourceLoader.getResource(location);

        beanDefinitionReader.loadBeanDefinitions(new Resource[]{resource});
    }

    @Test
    public void should_load_bean_definitions_by_location() {
        String location = "classpath:xml-bean-definition-reader.xml";
        beanDefinitionReader.loadBeanDefinitions(location);
    }

    @Test
    public void should_load_bean_definitions_by_locations() {
        String location = "classpath:xml-bean-definition-reader.xml";
        beanDefinitionReader.loadBeanDefinitions(new String[]{location});
    }

    @Test
    public void should_throw_beans_exception_to_load_bean_definitions_when_resource_not_found() {
        String location = RandomUtil.randomString(10);
        ResourceLoader resourceLoader = beanDefinitionReader.getResourceLoader();
        Resource resource = resourceLoader.getResource(location);

        Assertions.assertThatExceptionOfType(BeansException.class)
                .isThrownBy(() -> beanDefinitionReader.loadBeanDefinitions(resource));
    }

    @Test
    public void should_throw_beans_exception_to_load_bean_definitions_when_component_scan_without_base_package() {
        String location = "classpath:xml-bean-definition-reader-without-base-package.xml";
        ResourceLoader resourceLoader = beanDefinitionReader.getResourceLoader();
        Resource resource = resourceLoader.getResource(location);

        Assertions.assertThatExceptionOfType(BeansException.class)
                .isThrownBy(() -> beanDefinitionReader.loadBeanDefinitions(resource));
    }

    @Test
    public void should_throw_beans_exception_to_load_bean_definitions_when_bean_definition_registry_contains_bean_definition() {
        Mockito.when(beanDefinitionRegistry.containsBeanDefinition(ArgumentMatchers.anyString()))
                .thenReturn(true);
        String location = "classpath:xml-bean-definition-reader.xml";
        ResourceLoader resourceLoader = beanDefinitionReader.getResourceLoader();
        Resource resource = resourceLoader.getResource(location);

        Assertions.assertThatExceptionOfType(BeansException.class)
                .isThrownBy(() -> beanDefinitionReader.loadBeanDefinitions(resource));
    }
}
