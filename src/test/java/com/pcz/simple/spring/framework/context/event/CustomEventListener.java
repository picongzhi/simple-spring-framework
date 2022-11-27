package com.pcz.simple.spring.framework.context.event;

import com.pcz.simple.spring.framework.context.ApplicationListener;

public class CustomEventListener implements ApplicationListener<CustomEvent> {
    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("On customEvent" +
                ", source: " + event.getSource()
                + ", id: " + event.getId()
                + ", message: " + event.getMessage());
    }
}
