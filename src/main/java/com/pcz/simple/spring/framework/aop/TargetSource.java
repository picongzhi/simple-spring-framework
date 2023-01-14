package com.pcz.simple.spring.framework.aop;

import com.pcz.simple.spring.framework.util.ClassUtils;

/**
 * 被代理的目标对象
 *
 * @author picongzhi
 */
public class TargetSource {
    /**
     * 被代理的目标对象
     */
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     * 获取被代理的目标对象
     *
     * @return 被代理的目标对象
     */
    public Object getTarget() {
        return target;
    }

    /**
     * 获取被代理的目标类型
     *
     * @return 被代理的目标类型
     */
    public Class<?>[] getTargetClass() {
        Class<?> cls = this.target.getClass();
        cls = ClassUtils.isCglibProxyClass(cls) ? cls.getSuperclass() : cls;
        return cls.getInterfaces();
    }
}
