package com.pcz.simple.spring.framework.aop.aspectj;

import com.pcz.simple.spring.framework.aop.Pointcut;
import com.pcz.simple.spring.framework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * 基于 AspecetJ 表达式的切入点访问者
 *
 * @author picongzhi
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {
    /**
     * 基于AspectJ 表达式的切入点
     */
    private AspectJExpressionPointcut pointcut;

    /**
     * 通知
     */
    private Advice advice;

    /**
     * 切入点表达式
     */
    private String expression;

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public Pointcut getPointcut() {
        if (pointcut == null) {
            pointcut = new AspectJExpressionPointcut(expression);
        }

        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
