package com.pcz.simple.spring.framework.context.event;

/**
 * 上下文刷新事件
 *
 * @author picongzhi
 */
public class ContextRefreshedEvent extends ApplicationContextEvent {
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
