package com.pcz.simple.spring.framework.context.support;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.ConfigurableListableBeanFactory;
import com.pcz.simple.spring.framework.beans.factory.config.BeanFactoryPostProcessor;
import com.pcz.simple.spring.framework.beans.factory.config.BeanPostProcessor;
import com.pcz.simple.spring.framework.context.ApplicationEvent;
import com.pcz.simple.spring.framework.context.ApplicationListener;
import com.pcz.simple.spring.framework.context.ConfigurableApplicationContext;
import com.pcz.simple.spring.framework.context.event.ApplicationEventMulticaster;
import com.pcz.simple.spring.framework.context.event.ContextClosedEvent;
import com.pcz.simple.spring.framework.context.event.ContextRefreshedEvent;
import com.pcz.simple.spring.framework.context.event.SimpleApplicationEventMulticaster;
import com.pcz.simple.spring.framework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * 抽象的应用上下文
 *
 * @author picongzhi
 */
public abstract class AbstractApplicationContext
        extends DefaultResourceLoader
        implements ConfigurableApplicationContext {
    /**
     * 应用事件广播器 bean 名称
     */
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    /**
     * 应用事件广播器
     */
    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
        // 1. 刷新 BeanFactory
        refreshBeanFactory();

        // 2. 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3. 添加 ApplicationContextAwareProcessor
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 4. 执行 BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // 5. 注册 BeanPostProcessor
        registerBeanPostProcessors(beanFactory);

        // 6. 初始化事件广播器
        initApplicationEventMulticaster();

        // 7. 注册事件监听器
        registerListeners();

        // 8. 提前实例化单例 bean
        beanFactory.preInstantiateSingletons();

        // 9. 结束刷新，发布应用刷新事件
        finishRefresh();
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        // 发布应用上下文关闭事件
        publishEvent(new ContextClosedEvent(this));

        // 执行单例销毁方法
        getBeanFactory().destroySingletons();
    }

    /**
     * 刷新 BeanFactory
     * 创建 BeanFactory，并加载 BeanDefinition
     *
     * @throws BeansException Bean 异常
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    /**
     * 获取 BeanFactory
     *
     * @return 可配置的 BeanFactory
     * @see ConfigurableListableBeanFactory
     */
    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * 执行 BeanFactoryPostProcessor
     *
     * @param beanFactory 可配置的 BeanFactory
     * @see BeanFactoryPostProcessor
     */
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beansOfBeanFactoryPostProcessor =
                beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beansOfBeanFactoryPostProcessor.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * 注册 BeanPostProcessor
     *
     * @param beanFactory 可配置的 BeanFactory
     * @see BeanPostProcessor
     */
    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beansOfBeanPostProcessor =
                beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beansOfBeanPostProcessor.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    /**
     * 初始化应用事件广播器
     */
    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, this.applicationEventMulticaster);
    }

    /**
     * 注册监听器
     */
    private void registerListeners() {
        Collection<ApplicationListener> listeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : listeners) {
            this.applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    /**
     * 结束刷新，发布应用上下文刷新事件
     */
    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.applicationEventMulticaster.multicastEvent(event);
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return getBeanFactory().getBean(beanName, args);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(beanName, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }
}
