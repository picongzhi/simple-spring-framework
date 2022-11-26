package com.pcz.simple.spring.framework.beans.factory;

import com.pcz.simple.spring.framework.beans.BeansException;

/**
 * 感知当前 bean 所属的 BeanFactory
 *
 * @author picongzhi
 */
public interface BeanFactoryAware extends Aware {
    /**
     * 设置当前 bean 所属的 BeanFactory
     *
     * @param beanFactory BeanFactory
     * @throws BeansException bean 异常
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
