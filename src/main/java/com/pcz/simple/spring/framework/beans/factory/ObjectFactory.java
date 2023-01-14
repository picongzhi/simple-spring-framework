package com.pcz.simple.spring.framework.beans.factory;

import com.pcz.simple.spring.framework.beans.BeansException;

/**
 * 对象工厂
 *
 * @param <T>
 * @author picongzhi
 */
public interface ObjectFactory<T> {
    /**
     * 获取对象
     *
     * @return 对象
     * @throws BeansException Bean 异常
     */
    T getObject() throws BeansException;
}
