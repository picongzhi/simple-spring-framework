package com.pcz.simple.spring.framework.aop;

import org.aopalliance.aop.Advice;

/**
 * 访问者
 * 获取 Advice
 *
 * @author picongzhi
 */
public interface Advisor {
    /**
     * 获取通知
     *
     * @return 通知
     */
    Advice getAdvice();
}
