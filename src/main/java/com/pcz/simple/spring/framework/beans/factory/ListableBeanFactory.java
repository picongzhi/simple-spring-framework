package com.pcz.simple.spring.framework.beans.factory;

import java.util.Map;

/**
 * 可罗列的 BeanFactory
 *
 * @author picongzhi
 */
public interface ListableBeanFactory extends BeanFactory {
    /**
     * 返回所有类型是 type 的
     * bean name -> bean 实例映射
     *
     * @param type 类型
     * @param <T>  泛型
     * @return bean name -> bean 实例映射
     */
    <T> Map<String, T> getBeansOfType(Class<T> type);

    /**
     * 获取所有的 BeanDefinition 名称
     *
     * @return 所有的 BeanDefinition 名称
     */
    String[] getBeanDefinitionNames();
}
