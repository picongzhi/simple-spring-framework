package com.pcz.simple.spring.framework.aop;

/**
 * 切入点访问者
 * 组合 Pointcut 和 Advice
 *
 * @author picongzhi
 */
public interface PointcutAdvisor extends Advisor {
    /**
     * 获取 Pointcut
     *
     * @return Pointcut
     */
    Pointcut getPointcut();
}
