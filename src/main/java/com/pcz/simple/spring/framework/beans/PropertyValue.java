package com.pcz.simple.spring.framework.beans;

/**
 * 属性值
 *
 * @author picongzhi
 */
public class PropertyValue {
    /**
     * 属性名
     */
    private final String name;

    /**
     * 属性值
     */
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
