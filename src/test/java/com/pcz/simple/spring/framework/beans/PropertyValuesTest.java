package com.pcz.simple.spring.framework.beans;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PropertyValuesTest {
    @Test
    public void should_add_property_value() {
        String name = "name";
        String value = "value";
        PropertyValue propertyValue = new PropertyValue(name, value);

        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(propertyValue);
    }

    @Test
    public void should_get_property_values() {
        String name = "name";
        String value = "value";
        PropertyValue propertyValue = new PropertyValue(name, value);

        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(propertyValue);

        PropertyValue[] values = propertyValues.getPropertyValues();
        Assertions.assertThat(values).hasSize(1);
    }

    @Test
    public void should_get_property_value() {
        String name = "name";
        String value = "value";
        PropertyValue propertyValue = new PropertyValue(name, value);

        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(propertyValue);

        PropertyValue result = propertyValues.getPropertyValue(name);
        Assertions.assertThat(result).isEqualTo(propertyValue);
    }
}
