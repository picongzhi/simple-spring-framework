package com.pcz.simple.spring.framework.context.event;

import com.pcz.simple.spring.framework.context.ApplicationListener;

public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("On contextRefreshedEvent, source: " + event.getSource());
    }
}
