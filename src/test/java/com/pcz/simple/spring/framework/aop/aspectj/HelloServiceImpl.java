package com.pcz.simple.spring.framework.aop.aspectj;

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello() {
        return "hello";
    }
}
