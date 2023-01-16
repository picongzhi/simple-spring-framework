package com.pcz.simple.spring.framework.beans;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PropertyValueTest {
    @Test
    public void should_instantiation() {
        String name = "name";
        String value = "value";

        PropertyValue propertyValue = new PropertyValue(name, value);
        Assertions.assertThat(propertyValue).isNotNull();
        Assertions.assertThat(propertyValue.getName()).isEqualTo(name);
        Assertions.assertThat(propertyValue.getValue()).isEqualTo(value);
    }
}
