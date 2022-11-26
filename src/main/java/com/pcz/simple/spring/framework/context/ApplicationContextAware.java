package com.pcz.simple.spring.framework.context;

import com.pcz.simple.spring.framework.beans.factory.Aware;

/**
 * 感知当前 bean 所属的 ApplicationContext
 *
 * @author picongzhi
 */
public interface ApplicationContextAware extends Aware {
    /**
     * 设置当前 bean 所属的 ApplicationContext
     *
     * @param applicationContext ApplicationContext
     */
    void setApplicationContext(ApplicationContext applicationContext);
}
