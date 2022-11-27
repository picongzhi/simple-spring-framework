package com.pcz.simple.spring.framework.context.event;

import cn.hutool.core.util.RandomUtil;
import com.pcz.simple.spring.framework.context.support.ClassPathXmlApplicationContext;
import org.junit.jupiter.api.Test;

public class ApplicationContextEventTest {
    @Test
    public void should_publish_event() {
        String path = "classpath:spring-listener.xml";
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        applicationContext.publishEvent(new CustomEvent(applicationContext, RandomUtil.randomLong(), RandomUtil.randomString(20)));
        applicationContext.registerShutdownHook();
    }
}
