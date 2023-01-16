package com.pcz.simple.spring.framework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.DisposableBean;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * Disposable 适配器
 *
 * @author picongzhi
 */
public class DisposableBeanAdapter implements DisposableBean {
    /**
     * bean 实例
     */
    private final Object bean;

    /**
     * bean name
     */
    private final String beanName;

    /**
     * 销毁方法名
     */
    private final String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        // 执行 DisposableBean 接口
        if (this.bean instanceof DisposableBean) {
            ((DisposableBean) this.bean).destroy();
        }

        // 执行 destroy-method 方法，避免重复执行
        if (StrUtil.isNotEmpty(this.destroyMethodName)
                && !(bean instanceof DisposableBean
                && "destroy".equals(this.destroyMethodName))) {
            Method destroyMethod = null;
            try {
                destroyMethod = this.bean.getClass().getMethod(this.destroyMethodName);
            } catch (NoSuchMethodException | SecurityException e) {
                throw new BeansException("Couldn't find destroy method named '" + this.destroyMethodName +
                        "' on bean with name '" + this.beanName + "'");
            }

            destroyMethod.invoke(this.bean);
        }
    }
}
