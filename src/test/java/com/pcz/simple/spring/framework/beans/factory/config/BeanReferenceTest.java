package com.pcz.simple.spring.framework.beans.factory.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class BeanReferenceTest {
    @Test
    public void should_get_bean_name() {
        String beanName = "name";
        BeanReference beanReference = new BeanReference(beanName);

        String result = beanReference.getBeanName();
        Assertions.assertThat(result).isEqualTo(beanName);
    }
}
