package com.pcz.simple.spring.framework.beans.factory;

/**
 * 初始化 bean
 *
 * @author picongzhi
 */
public interface InitializingBean {
    /**
     * 在 bean 属性填充完后调用
     *
     * @throws Exception 初始化异常
     */
    void afterPropertiesSet() throws Exception;
}
