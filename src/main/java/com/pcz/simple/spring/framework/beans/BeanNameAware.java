package com.pcz.simple.spring.framework.beans;

import com.pcz.simple.spring.framework.beans.factory.Aware;

/**
 * 感知当前 bean 的名称
 *
 * @author picongzhi
 */
public interface BeanNameAware extends Aware {
    /**
     * 设置当前 bean 的名称
     *
     * @param beanName bean name
     */
    void setBeanName(String beanName);
}
