package com.pcz.simple.spring.framework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性集合
 *
 * @author picongzhi
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValues = new ArrayList<>();

    /**
     * 添加属性
     *
     * @param propertyValue 属性
     */
    public void addPropertyValue(PropertyValue propertyValue) {
        this.propertyValues.add(propertyValue);
    }

    /**
     * 获取所有属性
     *
     * @return 所有属性的数组
     */
    public PropertyValue[] getPropertyValues() {
        return this.propertyValues.toArray(new PropertyValue[0]);
    }

    /**
     * 根据属性名获取属性
     *
     * @param propertyName 属性名
     * @return 属性
     */
    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue propertyValue : this.propertyValues) {
            if (propertyValue.getName().equals(propertyName)) {
                return propertyValue;
            }
        }

        return null;
    }
}
