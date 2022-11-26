package com.pcz.simple.spring.framework.context;

import java.util.EventObject;

/**
 * 应用事件
 *
 * @author picongzhi
 */
public abstract class ApplicationEvent extends EventObject {
    public ApplicationEvent(Object source) {
        super(source);
    }
}
