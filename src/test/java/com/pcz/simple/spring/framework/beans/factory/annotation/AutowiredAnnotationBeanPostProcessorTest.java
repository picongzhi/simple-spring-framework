package com.pcz.simple.spring.framework.beans.factory.annotation;

import com.pcz.simple.spring.framework.beans.PropertyValues;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import com.pcz.simple.spring.framework.beans.factory.support.DefaultListableBeanFactory;
import com.pcz.simple.spring.framework.util.StringValueResolver;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AutowiredAnnotationBeanPostProcessorTest {
    private AutowiredAnnotationBeanPostProcessor processor;

    private DefaultListableBeanFactory beanFactory;

    @BeforeEach
    public void setUp() {
        beanFactory = new DefaultListableBeanFactory();
        beanFactory.addEmbeddedValueResolver(new DefaultStringValueResolver());

        processor = new AutowiredAnnotationBeanPostProcessor();
        processor.setBeanFactory(beanFactory);
    }

    @Test
    public void should_post_process_property_values() {
        beanFactory.registerBeanDefinition(
                "helloRepository", new BeanDefinition(HelloRepository.class));
        beanFactory.registerBeanDefinition(
                "helloService", new BeanDefinition(HelloService.class));

        String beanName = "helloService";
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
        HelloService helloService = beanFactory.getBean(HelloService.class);

        PropertyValues propertyValues = processor.postProcessPropertyValues(beanDefinition.getPropertyValues(), helloService, beanName);
        Assertions.assertThat(propertyValues).isNotNull();
        Assertions.assertThat(helloService.getName()).isNotNull();
        Assertions.assertThat(helloService.hello()).isNotNull();
    }

    private static class HelloService {
        @Value("${name}")
        private String name;

        @Autowired
        private HelloRepository helloRepository;

        public HelloService() {
        }

        public String getName() {
            return name;
        }

        public String hello() {
            return helloRepository.hello();
        }
    }

    private static class HelloRepository {
        public HelloRepository() {
        }

        public String hello() {
            return "hello";
        }
    }

    private static class DefaultStringValueResolver implements StringValueResolver {
        @Override
        public String resolveStringValue(String value) {
            return "pcz";
        }
    }
}
