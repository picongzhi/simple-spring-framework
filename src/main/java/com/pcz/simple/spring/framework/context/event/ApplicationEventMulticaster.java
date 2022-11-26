package com.pcz.simple.spring.framework.context.event;

import com.pcz.simple.spring.framework.context.ApplicationEvent;
import com.pcz.simple.spring.framework.context.ApplicationListener;

/**
 * 应用事件广播器
 *
 * @author picongzhi
 */
public interface ApplicationEventMulticaster {
    /**
     * 添加应用事件监听器
     *
     * @param listener 应用事件监听器
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 删除应用事件监听器
     *
     * @param listener 应用事件监听器
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 广播应用事件
     *
     * @param event 应用事件
     */
    void multicastEvent(ApplicationEvent event);
}
