package com.pcz.simple.spring.framework.context.event;

import com.pcz.simple.spring.framework.beans.factory.BeanFactory;
import com.pcz.simple.spring.framework.beans.factory.support.DefaultListableBeanFactory;
import com.pcz.simple.spring.framework.context.ApplicationContext;
import com.pcz.simple.spring.framework.context.ApplicationEvent;
import com.pcz.simple.spring.framework.context.ApplicationListener;
import com.pcz.simple.spring.framework.context.support.ClassPathXmlApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApplicationEventMulticasterTest {
    private ApplicationEventMulticaster applicationEventMulticaster;

    private BeanFactory beanFactory;

    @BeforeEach
    public void setUp() {
        beanFactory = new DefaultListableBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
    }

    @Test
    public void should_add_application_listener() {
        applicationEventMulticaster.addApplicationListener(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                // handle application event
            }
        });
    }

    @Test
    public void should_remove_application_listener() {
        ApplicationListener<ApplicationEvent> listener = new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                // handle application event
            }
        };
        applicationEventMulticaster.addApplicationListener(listener);
        applicationEventMulticaster.removeApplicationListener(listener);
    }

    @Test
    public void should_multicaset_event() {
        ApplicationListener<ContextRefreshedEvent> listener = new ApplicationListener<ContextRefreshedEvent>() {
            @Override
            public void onApplicationEvent(ContextRefreshedEvent event) {
                ApplicationContext applicationContext = event.getApplicationContext();
            }
        };
        applicationEventMulticaster.addApplicationListener(listener);
        applicationEventMulticaster.multicastEvent(new ContextRefreshedEvent(new ClassPathXmlApplicationContext()));
    }
}
