package com.pcz.simple.spring.framework.context;

import java.util.EventListener;

/**
 * 应用监听器
 *
 * @param <E> 事件泛型
 * @author picongzhi
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    /**
     * 处理应用事件
     *
     * @param event 应用事件
     */
    void onApplicationEvent(E event);
}
