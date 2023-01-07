package com.pcz.simple.spring.framework.aop.aspectj;

import com.pcz.simple.spring.framework.aop.ClassFilter;
import com.pcz.simple.spring.framework.aop.MethodMatcher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class AspectJExpressionPointcutTest {
    private AspectJExpressionPointcut pointcut;

    @BeforeEach
    public void setUp() {
        String expression = "execution(* com.pcz.simple.spring.framework.aop.aspectj.HelloService.*(..))";
        pointcut = new AspectJExpressionPointcut(expression);
    }

    @Test
    public void should_match_class() {
        boolean match = pointcut.matches(HelloService.class);
        Assertions.assertThat(match).isTrue();
    }

    @Test
    public void should_not_match_class() {
        boolean match = pointcut.matches(DemoService.class);
        Assertions.assertThat(match).isFalse();
    }

    @Test
    public void should_match_method() throws Throwable {
        Class<?> cls = HelloService.class;
        Method method = cls.getMethod("hello");

        boolean match = pointcut.matches(method, cls);
        Assertions.assertThat(match).isTrue();
    }

    @Test
    public void should_not_match_method() throws Throwable {
        Class<?> cls = DemoService.class;
        Method method = cls.getMethod("demo");

        boolean match = pointcut.matches(method, cls);
        Assertions.assertThat(match).isFalse();
    }

    @Test
    public void should_get_class_filter() {
        ClassFilter classFilter = pointcut.getClassFilter();
        Assertions.assertThat(classFilter).isNotNull();
    }

    @Test
    public void should_get_method_matcher() {
        MethodMatcher methodMatcher = pointcut.getMethodMatcher();
        Assertions.assertThat(methodMatcher).isNotNull();
    }
}
