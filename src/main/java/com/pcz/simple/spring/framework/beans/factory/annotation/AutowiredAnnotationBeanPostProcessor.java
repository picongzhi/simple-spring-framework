package com.pcz.simple.spring.framework.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.PropertyValues;
import com.pcz.simple.spring.framework.beans.factory.BeanFactory;
import com.pcz.simple.spring.framework.beans.factory.BeanFactoryAware;
import com.pcz.simple.spring.framework.beans.factory.ConfigurableListableBeanFactory;
import com.pcz.simple.spring.framework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.pcz.simple.spring.framework.util.ClassUtils;

import java.lang.reflect.Field;

/**
 * 基于注解自动注入的 Bean 后置处理器
 *
 * @author picongzhi
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    /**
     * BeanFactory
     */
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeansException {
        Class<?> beanClass = ClassUtils.isCglibProxyClass(bean.getClass()) ?
                bean.getClass().getSuperclass() :
                bean.getClass();

        // 处理 Value 注解
        Field[] fields = beanClass.getDeclaredFields();
        for (Field field : fields) {
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (valueAnnotation != null) {
                String value = beanFactory.resolveEmbeddedValue(valueAnnotation.value());
                BeanUtil.setFieldValue(bean, field.getName(), value);
            }
        }

        // 处理 Autowired 注解
        for (Field field : fields) {
            Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
            if (autowiredAnnotation != null) {
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                String dependentBeanName = qualifierAnnotation != null ?
                        qualifierAnnotation.value() :
                        null;
                Object dependentBean = StrUtil.isNotEmpty(dependentBeanName) ?
                        beanFactory.getBean(dependentBeanName, field.getType()) :
                        beanFactory.getBean(field.getType());
                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }

        return propertyValues;
    }
}
