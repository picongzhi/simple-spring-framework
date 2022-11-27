package com.pcz.simple.spring.framework.aop;

/**
 * Class 过滤器
 *
 * @author picongzhi
 */
public interface ClassFilter {
    /**
     * Class 实例是否匹配
     *
     * @param cls Class 实例
     * @return 是否匹配
     */
    boolean matches(Class<?> cls);
}
