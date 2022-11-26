package com.pcz.simple.spring.framework.beans.factory;

/**
 * 销毁 bean
 *
 * @author picongzhi
 */
public interface DisposableBean {
    /**
     * 执行销毁
     *
     * @throws Exception 销毁异常
     */
    void destroy() throws Exception;
}
