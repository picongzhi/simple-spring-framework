package com.pcz.simple.spring.framework.context;

import com.pcz.simple.spring.framework.beans.BeansException;

/**
 * 可配置的应用上下文
 *
 * @author picongzhi
 */
public interface ConfigurableApplicationContext extends ApplicationContext {
    /**
     * 刷新应用上下文
     *
     * @throws BeansException Bean 异常
     */
    void refresh() throws BeansException;

    /**
     * 注册 Shutdown 的回调
     */
    void registerShutdownHook();

    /**
     * 关闭应用上下文
     */
    void close();
}
