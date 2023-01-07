package com.pcz.simple.spring.framework.context.annotation;

import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class ClassPathScanningCandidateComponentProviderTest {
    private ClassPathScanningCandidateComponentProvider provider;

    @BeforeEach
    public void setUp() {
        provider = new ClassPathScanningCandidateComponentProvider();
    }

    @Test
    public void should_find_candidate_components() {
        String backPackage = "com.pcz.simple.spring.framework.context.annotation";
        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(backPackage);
        Assertions.assertThat(beanDefinitions).hasSize(1);
    }
}
