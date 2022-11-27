package com.pcz.simple.spring.framework.context.event;

import com.pcz.simple.spring.framework.context.ApplicationListener;

public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("On contextClosedEvent, source: " + event.getSource());
    }
}
