package com.pcz.simple.spring.framework.beans.factory.support;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 支持 FactoryBean 注册
 *
 * @author picongzhi
 */
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
    /**
     * FactoryBean 对象实例缓存
     * beanName -> FactoryBean 对象实例
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    /**
     * 根据 bean 名称获取缓存的 FactoryBean 对象实例
     *
     * @param beanName bean 名称
     * @return FactoryBean 对象实例
     */
    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);
        return object != NULL_OBJECT ? object : null;
    }

    /**
     * 从 FactoryBean 获取对象实例
     *
     * @param factoryBean FactoryBean
     * @param beanName    bean 名称
     * @return FactoryBean 对象实例
     */
    protected Object getObjectFromFactoryBean(FactoryBean factoryBean, String beanName) {
        if (!factoryBean.isSingleton()) {
            return doGetObjectFromFactoryBean(factoryBean, beanName);
        }

        Object object = this.factoryBeanObjectCache.get(beanName);
        if (object == null) {
            object = doGetObjectFromFactoryBean(factoryBean, beanName);
            this.factoryBeanObjectCache.put(beanName, object != null ? object : NULL_OBJECT);
        }

        return object != NULL_OBJECT ? object : null;
    }

    /**
     * 从 FactoryBean 获取对象实例
     *
     * @param factoryBean FactoryBean
     * @param beanName    bean 名称
     * @return FactoryBean 对象实例
     */
    private Object doGetObjectFromFactoryBean(FactoryBean factoryBean, String beanName) {
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }
}
