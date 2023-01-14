package com.pcz.simple.spring.framework.beans.factory.support;

import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.DisposableBean;
import com.pcz.simple.spring.framework.beans.factory.ObjectFactory;
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
     * 一级缓存，成品 bean
     * bean name -> bean 单例
     */
    private final Map<String, Object> singletonObjects = new HashMap<>();

    /**
     * 二级缓存，半成品 bean
     * 实例化但没有给属性初始化赋值，提前暴露对象
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>();

    /**
     * 三级缓存，工厂 bean
     * 用于存储代理对象，确保 bean 先初始化完成，再处理代理对象的初始化
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();

    /**
     * bean name -> DisposableBean 实例
     */
    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        Object singletonObject = this.singletonObjects.get(beanName);
        if (singletonObject == null) {
            // 成品 bean 为 null
            singletonObject = this.earlySingletonObjects.get(beanName);
            if (singletonObject == null) {
                // 半成品 bean 为 null
                ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    // 通过工厂获取 bean
                    singletonObject = singletonFactory.getObject();

                    // 将半成品 bean 添加到二级缓存
                    this.earlySingletonObjects.put(beanName, singletonObject);
                    // 将工厂 bean 从三级缓存清除
                    this.singletonFactories.remove(beanName);
                }
            }
        }

        return singletonObject;
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        // 将单例bean添加到一级缓存
        this.singletonObjects.put(beanName, singletonObject);

        // 将半成品 bean 从二级缓存清除
        this.earlySingletonObjects.remove(beanName);

        // 将工厂 bean 从三级缓存清除
        this.singletonFactories.remove(beanName);
    }

    /**
     * 新增单例工厂 bean
     *
     * @param beanName         bean 名称
     * @param singletonFactory 单例工厂 bean
     */
    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        if (!this.singletonFactories.containsKey(beanName)) {
            // 将工厂 bean 添加到三级缓存
            this.singletonFactories.put(beanName, singletonFactory);

            // 将半成品 bean 从二级缓存清除
            this.earlySingletonObjects.remove(beanName);
        }
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
