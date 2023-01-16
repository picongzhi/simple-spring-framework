package com.pcz.simple.spring.framework.beans.factory;

import com.pcz.simple.spring.framework.beans.PropertyValue;
import com.pcz.simple.spring.framework.beans.PropertyValues;
import com.pcz.simple.spring.framework.beans.factory.xml.XmlBeanDefinitionReader;
import com.pcz.simple.spring.framework.common.*;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import com.pcz.simple.spring.framework.beans.factory.config.BeanReference;
import com.pcz.simple.spring.framework.beans.factory.support.DefaultListableBeanFactory;
import com.pcz.simple.spring.framework.beans.factory.support.SimpleInstantiationStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class BeanFactoryTest {
    private DefaultListableBeanFactory beanFactory;

    @BeforeEach
    public void setUp() {
        beanFactory = new DefaultListableBeanFactory();
    }

    @Test
    public void should_get_bean() {
        String beanName = "userService";
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);

        UserService userService = (UserService) beanFactory.getBean(beanName);
        Assertions.assertThat(userService).isNotNull();

        userService.hello();
    }

    @Test
    public void should_get_bean_when_constructor_args_provided() {
        String beanName = "userService";
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);

        UserService userService = (UserService) beanFactory.getBean(beanName, "1");
        Assertions.assertThat(userService).isNotNull();

        userService.hello();
    }

    @Test
    public void should_get_bean_when_instantiate_with_simple_instantiation_strategy() {
        beanFactory.setInstantiationStrategy(new SimpleInstantiationStrategy());

        String beanName = "userService";
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);

        UserService userService = (UserService) beanFactory.getBean(beanName, "1");
        Assertions.assertThat(userService).isNotNull();

        userService.hello();
    }

    @Test
    public void should_get_bean_when_property_values_provided() {
        String userDaoName = "userDao";
        beanFactory.registerBeanDefinition(userDaoName, new BeanDefinition(UserDaoFactoryBean.class));

        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("id", "1"));
        propertyValues.addPropertyValue(new PropertyValue(userDaoName, new BeanReference(userDaoName)));

        String userServiceName = "userService";
        beanFactory.registerBeanDefinition(userServiceName, new BeanDefinition(UserService.class, propertyValues));

        UserService userService = (UserService) beanFactory.getBean(userServiceName);
        userService.showUserInfo();
    }

    @Test
    public void should_get_bean_when_path_is_xml() {
        String path = "classpath:spring.xml";
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(path);

        String userServiceName = "userService";
        UserService userService = beanFactory.getBean(userServiceName, UserService.class);
        Assertions.assertThat(userService).isNotNull();
        userService.showUserInfo();
    }

    @Test
    public void should_get_beans_of_type() {
        String path = "classpath:spring.xml";
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(path);

        Map<String, UserService> beansOfUserService = beanFactory.getBeansOfType(UserService.class);
        Assertions.assertThat(beansOfUserService).isNotEmpty();
    }

    @Test
    public void should_get_bean_definition_names() {
        String path = "classpath:spring.xml";
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(path);

        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        Assertions.assertThat(beanDefinitionNames).isNotEmpty();
    }

    @Test
    public void should_get_bean_when_bean_factory_post_processor_processed() {
        String path = "classpath:spring.xml";
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(path);

        MyBeanFactoryPostProcessor myBeanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        myBeanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        UserService userService = beanFactory.getBean("userService", UserService.class);
        Assertions.assertThat(userService).isNotNull();
        userService.showUserInfo();
    }

    @Test
    public void should_get_bean_when_bean_post_processor_processed() {
        String path = "classpath:spring.xml";
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(path);

        MyBeanPostProcessor myBeanPostProcessor = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(myBeanPostProcessor);

        UserService userService = beanFactory.getBean("userService", UserService.class);
        Assertions.assertThat(userService).isNotNull();
        userService.showUserInfo();
    }
}
