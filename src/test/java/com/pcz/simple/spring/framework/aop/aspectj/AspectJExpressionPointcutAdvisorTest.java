package com.pcz.simple.spring.framework.aop.aspectj;

import com.pcz.simple.spring.framework.aop.Pointcut;
import com.pcz.simple.spring.framework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AspectJExpressionPointcutAdvisorTest {
    private AspectJExpressionPointcutAdvisor advisor;

    @BeforeEach
    public void setUp() {
        advisor = new AspectJExpressionPointcutAdvisor();
    }

    @Test
    public void should_get_pointcut() {
        String expression = "execution(* com.pcz.simple.spring.framework.aop.aspectj.HelloService.*(..))";
        advisor.setExpression(expression);

        Pointcut pointcut = advisor.getPointcut();
        Assertions.assertThat(pointcut).isNotNull();
    }

    @Test
    public void should_get_advice() {
        MethodInterceptor methodInterceptor = new MethodBeforeAdviceInterceptor(new LogBeforeAdvice());
        advisor.setAdvice(methodInterceptor);

        Advice advice = advisor.getAdvice();
        Assertions.assertThat(advice).isNotNull();
    }
}
