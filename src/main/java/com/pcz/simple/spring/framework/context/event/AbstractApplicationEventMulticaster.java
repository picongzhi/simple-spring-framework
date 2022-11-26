package com.pcz.simple.spring.framework.context.event;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.BeanFactory;
import com.pcz.simple.spring.framework.beans.factory.BeanFactoryAware;
import com.pcz.simple.spring.framework.context.ApplicationEvent;
import com.pcz.simple.spring.framework.context.ApplicationListener;
import com.pcz.simple.spring.framework.util.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 抽象的应用事件广播器
 *
 * @author picongzhi
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {
    /**
     * 应用监听器
     */
    private final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    /**
     * BeanFactory
     */
    private BeanFactory beanFactory;

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        this.applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        this.applicationListeners.remove((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 获取应用事件监听器
     *
     * @param event 应用事件
     * @return 应用事件监听器
     */
    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event) {
        List<ApplicationListener> listeners = new LinkedList<>();
        for (ApplicationListener<ApplicationEvent> listener : this.applicationListeners) {
            if (supportEvent(listener, event)) {
                listeners.add(listener);
            }
        }

        return listeners;
    }

    protected boolean supportEvent(ApplicationListener<ApplicationEvent> listener, ApplicationEvent event) {
        Class<? extends ApplicationListener> listenerClass = listener.getClass();

        // 获取目标类，如果是 Cglib 代理类，则获取父类
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ?
                listenerClass.getSuperclass() : listenerClass;

        // 获取监听器的泛型类
        Type genericInterface = targetClass.getGenericInterfaces()[0];
        // 获取实际的泛型类型
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        // 获取实际的泛型类名
        String className = actualTypeArgument.getTypeName();

        // 获取Class实例
        Class<?> eventClassName = null;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("Wrong event class name: " + className);
        }

        return eventClassName.isAssignableFrom(event.getClass());
    }
}
