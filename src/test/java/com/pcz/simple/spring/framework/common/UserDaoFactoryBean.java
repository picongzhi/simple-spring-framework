package com.pcz.simple.spring.framework.common;

import cn.hutool.core.util.RandomUtil;
import com.pcz.simple.spring.framework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class UserDaoFactoryBean implements FactoryBean<UserDao> {
    @Override
    public UserDao getObject() throws Exception {
        InvocationHandler invocationHandler = (proxy, method, args) -> {
            Map<String, String> idNameMap = new HashMap<>();
            idNameMap.put("1", RandomUtil.randomString(10));
            idNameMap.put("2", RandomUtil.randomString(10));
            idNameMap.put("3", RandomUtil.randomString(10));

            String id = args[0].toString();

            return "你被代理了 " + method.getName() + ": " + idNameMap.get(id);
        };

        return (UserDao) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{UserDao.class},
                invocationHandler);
    }

    @Override
    public Class<?> getObjectType() {
        return UserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
