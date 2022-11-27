package com.pcz.simple.spring.framework.context.event;

public class CustomEvent extends ApplicationContextEvent {
    private Long id;

    private String message;

    public CustomEvent(Object source, Long id, String message) {
        super(source);
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
