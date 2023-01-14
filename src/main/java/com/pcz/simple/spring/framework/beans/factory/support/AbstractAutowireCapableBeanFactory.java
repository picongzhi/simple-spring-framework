package com.pcz.simple.spring.framework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.pcz.simple.spring.framework.beans.BeanNameAware;
import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.PropertyValue;
import com.pcz.simple.spring.framework.beans.PropertyValues;
import com.pcz.simple.spring.framework.beans.factory.*;
import com.pcz.simple.spring.framework.beans.factory.config.*;

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
            // 实例化前置处理，判断是否返回了代理对象
            bean = resolveBeforeInstantiation(beanName, beanDefinition);
            if (bean != null) {
                return bean;
            }

            // 实例化 bean
            bean = createBeanInstance(beanDefinition, beanName, args);

            // 实例化后置处理
            boolean continuePropertyPopulation = applyBeanPostProcessorsAfterInstantiation(beanName, bean);
            if (!continuePropertyPopulation) {
                return bean;
            }

            // 属性填充前置处理
            applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);

            // 属性填充
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
     * 实例化之前解析
     *
     * @param beanName       Bean 名称
     * @param beanDefinition BeanDefinition
     * @return Bean 实例
     */
    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if (bean != null) {
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
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
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // 调用初始化方法
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
        }

        // 执行 BeanPostProcessor 的后置处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);

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

    /**
     * 使用 BeanPostProcessor 进行实例化前置处理
     *
     * @param beanClass bean Class 实例
     * @param beanName  bean 名称
     * @return bean 实例
     */
    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor)
                        .postProcessBeforeInstantiation(beanClass, beanName);
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }

    /**
     * 使用 BeanPostProcessor 进行属性赋值前的处理
     *
     * @param beanName       bean 名称
     * @param bean           bean 实例
     * @param beanDefinition BeanDefinition
     */
    protected void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                PropertyValues propertyValues = ((InstantiationAwareBeanPostProcessor) beanPostProcessor)
                        .postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
                if (propertyValues != null) {
                    for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                    }
                }
            }
        }
    }

    /**
     * 应用 BeanPostProcessor 执行实例化后的处理
     *
     * @param beanName bean 名称
     * @param bean     bean 实例
     * @return 是否继续属性填充
     */
    protected boolean applyBeanPostProcessorsAfterInstantiation(String beanName, Object bean) {
        boolean continuePropertyPopulation = true;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                if (!((InstantiationAwareBeanPostProcessor) beanPostProcessor)
                        .postProcessAfterInstantiation(bean, beanName)) {
                    continuePropertyPopulation = false;
                    break;
                }
            }
        }

        return continuePropertyPopulation;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
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
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
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
