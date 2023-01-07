package com.pcz.simple.spring.framework.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import com.pcz.simple.spring.framework.beans.factory.support.BeanDefinitionRegistry;
import com.pcz.simple.spring.framework.stereotype.Component;

import java.util.Set;

/**
 * 基于ClassPath的BeanDefinition扫描器
 *
 * @author picongzhi
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    /**
     * BeanDefinition 注册器
     */
    private final BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 扫描
     *
     * @param basePackages 根路径
     */
    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidates) {
                // 解析作用域
                String scope = resolveBeanScope(beanDefinition);
                if (StrUtil.isNotEmpty(scope)) {
                    beanDefinition.setScope(scope);
                }

                // 确定 Bean 名称
                String beanName = determinBeanName(beanDefinition);

                // 注册 BeanDefinition
                registry.registerBeanDefinition(beanName, beanDefinition);
            }
        }
    }

    /**
     * 解析bean的作用域
     *
     * @param beanDefinition BeanDefinition
     * @return bean的作用域
     */
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        return scope == null ? StrUtil.EMPTY : scope.value();
    }

    /**
     * 确定Bean名称
     * 如果没有通过 {@link Component} 注解配置，默认取首字母小写的Class名
     *
     * @param beanDefinition BeanDefinition
     * @return Bean名称
     */
    private String determinBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String name = component.value();
        return StrUtil.isEmpty(name) ?
                StrUtil.lowerFirst(beanClass.getSimpleName()) :
                name;
    }
}
