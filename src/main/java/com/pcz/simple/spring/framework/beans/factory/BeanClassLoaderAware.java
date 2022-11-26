package com.pcz.simple.spring.framework.beans.factory;

/**
 * 感知加载当前 bean 的 ClassLoader
 *
 * @author picongzhi
 */
public interface BeanClassLoaderAware extends Aware {
    /**
     * 设置加载当前 bean 的 ClassLoader
     *
     * @param classLoader ClassLoader
     */
    public void setBeanClassLoader(ClassLoader classLoader);
}
