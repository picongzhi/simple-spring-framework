package com.pcz.simple.spring.framework.context.support;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.ConfigurableListableBeanFactory;
import com.pcz.simple.spring.framework.beans.factory.support.DefaultListableBeanFactory;

/**
 * 抽象的可刷新的应用上下文
 *
 * @author picongzhi
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
    /**
     * BeanFactory
     */
    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);

        this.beanFactory = beanFactory;
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    /**
     * 创建 BeanFactory
     *
     * @return DefaultListableBeanFactory
     */
    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    /**
     * 加载 BeanDefinition
     *
     * @param beanFactory DefaultListableBeanFactory
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);
}
