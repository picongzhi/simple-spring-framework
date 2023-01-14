package com.pcz.simple.spring.framework.beans.factory.support;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.DisposableBean;
import com.pcz.simple.spring.framework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 默认的单例 bean 注册器
 *
 * @author picongzhi
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    /**
     * 空对象
     */
    protected static final Object NULL_OBJECT = new Object();

    /**
     * bean name -> bean 单例
     */
    private Map<String, Object> singletonObjects = new HashMap<>();

    /**
     * bean name -> DisposableBean 实例
     */
    private Map<String, DisposableBean> disposableBeans = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        this.singletonObjects.put(beanName, singletonObject);
    }

    /**
     * 注册 DisposableBean
     *
     * @param beanName       bean name
     * @param disposableBean DisposableBean
     */
    public void registerDisposableBean(String beanName, DisposableBean disposableBean) {
        this.disposableBeans.put(beanName, disposableBean);
    }

    /**
     * 销毁单例
     */
    public void destroySingletons() {
        Set<String> disposableBeanNames = disposableBeans.keySet();
        String[] disposableBeanNameArray = disposableBeanNames.toArray(new String[0]);
        for (int i = disposableBeanNameArray.length - 1; i >= 0; i--) {
            String beanName = disposableBeanNameArray[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw exception", e);
            }
        }
    }
}
