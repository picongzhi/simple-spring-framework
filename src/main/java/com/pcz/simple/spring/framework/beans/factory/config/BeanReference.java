package com.pcz.simple.spring.framework.beans.factory.config;

/**
 * Bean 引用
 *
 * @author picongzhi
 */
public class BeanReference {
    /**
     * 引用的 bean name
     */
    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
