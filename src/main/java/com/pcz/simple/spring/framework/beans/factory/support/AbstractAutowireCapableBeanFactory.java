package com.pcz.simple.spring.framework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.pcz.simple.spring.framework.beans.BeanNameAware;
import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.PropertyValue;
import com.pcz.simple.spring.framework.beans.PropertyValues;
import com.pcz.simple.spring.framework.beans.factory.*;
import com.pcz.simple.spring.framework.beans.factory.config.AutowireCapableBeanFactory;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import com.pcz.simple.spring.framework.beans.factory.config.BeanPostProcessor;
import com.pcz.simple.spring.framework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 抽象的自动注入可装配的 bean 工厂
 *
 * @author picongzhi
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    /**
     * 实例化策略
     * 默认是 {@link CglibSubclassingInstantiationStrategy}
     */
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = null;
        try {
            // 实例化 bean
            bean = createBeanInstance(beanDefinition, beanName, args);

            // 给 bean 的属性赋值
            applyPropertyValues(beanName, bean, beanDefinition);

            // 初始化 bean
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 注册 DisposableBean
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        // 注册单例
        if (beanDefinition.isSingleton()) {
            registerSingleton(beanName, bean);
        }

        return bean;
    }

    /**
     * 创建 bean 实例
     *
     * @param beanDefinition BeanDefinition
     * @param beanName       bean name
     * @param args           构造方法参数
     * @return bean 实例
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor<?> constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();

        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        for (Constructor<?> constructor : declaredConstructors) {
            if (args != null && constructor.getParameterTypes().length == args.length) {
                constructorToUse = constructor;
                break;
            }
        }

        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
    }

    /**
     * 添加属性
     *
     * @param beanName       bean name
     * @param bean           bean 实例
     * @param beanDefinition BeanDefinition
     */
    private void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                if (value instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }

                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Applying " + beanName + " property values failed", e);
        }
    }

    /**
     * 初始化 bean
     *
     * @param beanName       bean name
     * @param bean           bean 实例
     * @param beanDefinition BeanDefinition
     * @return 初始化后的 bean 实例
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 调用 Aware 接口
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }

            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }

            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }

        // 执行 BeanPostProcessor 的前置处理
        Object wrappedBean = applyBeanPostProcessorBeforeInitialization(bean, beanName);

        // 调用初始化方法
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
        }

        // 执行 BeanPostProcessor 的后置处理
        wrappedBean = applyBeanPostProcessorAfterInitialization(bean, beanName);

        return wrappedBean;
    }

    /**
     * 调用初始化方法
     *
     * @param beanName       bean name
     * @param bean           bean 实例
     * @param beanDefinition BeanDefinition
     */
    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        // 执行 InitializingBean 接口
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        // 执行 init-method 方法
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (initMethod == null) {
                throw new BeansException("Could not find an init method named '" + initMethodName +
                        "' on bean with name '" + beanName + "'");
            }

            initMethod.invoke(bean);
        }
    }

    /**
     * 获取实例化策略
     *
     * @return 实例化策略
     */
    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    /**
     * 设置实例化策略
     *
     * @param instantiationStrategy 实例化策略
     */
    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }

            result = current;
        }

        return result;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }

            result = current;
        }

        return result;
    }

    /**
     * 注册 DisposableBean
     *
     * @param beanName       bean name
     * @param bean           bean 实例
     * @param beanDefinition BeanDefinition
     */
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (!beanDefinition.isSingleton()) {
            // 非单例不执行销毁方法
            return;
        }

        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }
}
