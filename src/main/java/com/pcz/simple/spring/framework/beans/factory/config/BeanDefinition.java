package com.pcz.simple.spring.framework.beans.factory.config;

import com.pcz.simple.spring.framework.beans.PropertyValues;

import java.util.Objects;

/**
 * Bean 定义
 *
 * @author picongzhi
 */
public class BeanDefinition {
    /**
     * 单例作用域
     */
    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

    /**
     * 原型作用域
     */
    String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    /**
     * bean Class 实例
     */
    private Class<?> beanClass;

    /**
     * 属性集合
     */
    private PropertyValues propertyValues;

    /**
     * 初始化方法名
     */
    private String initMethodName;

    /**
     * 销毁方法名
     */
    private String destroyMethodName;

    /**
     * 作用域
     * 默认是单例
     */
    private String scope = SCOPE_SINGLETON;

    /**
     * 是否是单例，默认是true
     */
    private boolean singleton = true;

    /**
     * 是否是原型，默认是false
     */
    private boolean prototype = false;

    public BeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class<?> beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ?
                propertyValues :
                new PropertyValues();
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    public boolean isSingleton() {
        return singleton;
    }

    public boolean isPrototype() {
        return prototype;
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "beanClass=" + beanClass +
                ", propertyValues=" + propertyValues +
                ", initMethodName='" + initMethodName + '\'' +
                ", destroyMethodName='" + destroyMethodName + '\'' +
                ", scope='" + scope + '\'' +
                ", singleton=" + singleton +
                ", prototype=" + prototype +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BeanDefinition that = (BeanDefinition) o;
        return singleton == that.singleton && prototype == that.prototype && Objects.equals(beanClass, that.beanClass) && Objects.equals(propertyValues, that.propertyValues) && Objects.equals(initMethodName, that.initMethodName) && Objects.equals(destroyMethodName, that.destroyMethodName) && Objects.equals(scope, that.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanClass, propertyValues, initMethodName, destroyMethodName, scope, singleton, prototype);
    }
}
