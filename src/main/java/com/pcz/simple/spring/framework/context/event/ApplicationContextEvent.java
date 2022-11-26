package com.pcz.simple.spring.framework.context.event;

import com.pcz.simple.spring.framework.context.ApplicationContext;
import com.pcz.simple.spring.framework.context.ApplicationEvent;

/**
 * 应用上下文事件
 *
 * @author picongzhi
 */
public class ApplicationContextEvent extends ApplicationEvent {
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * 获取应用上下文
     *
     * @return 应用上下文
     * @see ApplicationContext
     */
    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
