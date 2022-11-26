package com.pcz.simple.spring.framework.context.event;

/**
 * 上下文关闭事件
 *
 * @author picongzhi
 */
public class ContextClosedEvent extends ApplicationContextEvent {
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
