package com.pcz.simple.spring.framework.context;

import com.pcz.simple.spring.framework.common.UserService;
import com.pcz.simple.spring.framework.context.support.ClassPathXmlApplicationContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApplicationContextTest {
    @Test
    public void should_get_bean() {
        String path = "classpath:spring-postprocessor.xml";
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        applicationContext.registerShutdownHook();

        UserService userService = applicationContext.getBean("userService", UserService.class);
        Assertions.assertThat(userService).isNotNull();
        userService.showUserInfo();

        applicationContext.close();
    }
}
