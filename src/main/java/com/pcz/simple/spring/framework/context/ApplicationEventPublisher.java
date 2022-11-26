package com.pcz.simple.spring.framework.context;

/**
 * 应用事件发布器
 *
 * @author picongzhi
 */
public interface ApplicationEventPublisher {
    /**
     * 发布应用事件
     *
     * @param event 应用事件
     */
    void publishEvent(ApplicationEvent event);
}
