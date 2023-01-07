package com.pcz.simple.spring.framework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.pcz.simple.spring.framework.beans.factory.config.BeanDefinition;
import com.pcz.simple.spring.framework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 基于ClassPath扫描的候选组件提供器
 *
 * @author picongzhi
 */
public class ClassPathScanningCandidateComponentProvider {
    /**
     * 找到根路径下的所有获选组件
     *
     * @param basePackage 根路径
     * @return 候选组件 BeanDefinition
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();

        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> cls : classes) {
            candidates.add(new BeanDefinition(cls));
        }

        return candidates;
    }
}
