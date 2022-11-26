package com.pcz.simple.spring.framework.common;

import com.pcz.simple.spring.framework.beans.BeanNameAware;
import com.pcz.simple.spring.framework.beans.BeansException;
import com.pcz.simple.spring.framework.beans.factory.*;
import com.pcz.simple.spring.framework.context.ApplicationContext;
import com.pcz.simple.spring.framework.context.ApplicationContextAware;

public class UserService implements InitializingBean, DisposableBean,
        BeanNameAware, BeanClassLoaderAware, ApplicationContextAware, BeanFactoryAware {
    private String id;

    private UserDao userDao;

    private ApplicationContext applicationContext;

    private BeanFactory beanFactory;

    public UserService() {
        this.id = "1";
    }

    public UserService(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void hello() {
        System.out.println("hello " + this.id);
    }

    public void showUserInfo() {
        System.out.println(this.id + ": " + userDao.queryUserName(this.id));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(getClass().getName() + " afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(getClass().getName() + " destroy");
    }

    @Override
    public void setBeanName(String beanName) {
        System.out.println("Bean name is: " + beanName);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("ClassLoader is: " + classLoader);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
