package com.pcz.simple.spring.framework.core.io;

/**
 * 资源加载器
 *
 * @author picongzhi
 */
public interface ResourceLoader {
    /**
     * ClassPath 前缀
     */
    String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 获取资源
     *
     * @param location 资源路径
     * @return 资源
     */
    Resource getResource(String location);
}
