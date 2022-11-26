package com.pcz.simple.spring.framework.util;

/**
 * Class 工具类
 *
 * @author picongzhi
 */
public class ClassUtils {
    /**
     * 获取默认的 ClassLoader
     * 优先获取线程上下文的 ClassLoader，失败后降级到当前类的 ClassLoader
     *
     * @return ClassLoader
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = null;
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        } catch (Throwable t) {
        }

        if (classLoader != null) {
            return classLoader;
        }

        return ClassUtils.class.getClassLoader();
    }

    /**
     * 判断是否是 Cglib 代理类
     *
     * @param cls Class 实例
     * @return 是否是 Cglib 代理类
     */
    public static boolean isCglibProxyClass(Class<?> cls) {
        return cls != null && isCglibProxyClassName(cls.getName());
    }

    /**
     * 判断是否是 Cglib 代理类名
     *
     * @param className 类型
     * @return 是否是 Cglib 代理类名
     */
    public static boolean isCglibProxyClassName(String className) {
        return className != null && className.contains("$$");
    }
}
