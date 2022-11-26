package com.pcz.simple.spring.framework.context.support;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.config.BeanPostProcessor;
import com.pcz.simple.spring.framework.context.ApplicationContext;
import com.pcz.simple.spring.framework.context.ApplicationContextAware;

/**
 * {@link com.pcz.simple.spring.framework.context.ApplicationContextAware} 的后置处理器
 *
 * @author picongzhi
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {
    /**
     * 应用上下文
     */
    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
