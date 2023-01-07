package com.pcz.simple.spring.framework.aop.aspectj;

import com.pcz.simple.spring.framework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class LogBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("Before method: " + method.getName());
    }
}
