package com.pcz.simple.spring.framework.context.event;

import com.pcz.simple.spring.framework.beans.factory.BeanFactory;
import com.pcz.simple.spring.framework.context.ApplicationEvent;
import com.pcz.simple.spring.framework.context.ApplicationListener;

/**
 * 简单的应用事件广播器
 *
 * @author picongzhi
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {
    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
