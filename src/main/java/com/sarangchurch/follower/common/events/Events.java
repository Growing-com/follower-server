package com.sarangchurch.follower.common.events;

import org.springframework.context.ApplicationEventPublisher;

public class Events {
    static ApplicationEventPublisher publisher;

    static void setPublisher(ApplicationEventPublisher publisher) {
        if (publisher != null) {
            Events.publisher = publisher;
        }
    }

    public static void raise(Object event) {
        if (publisher != null) {
            publisher.publishEvent(event);
        }
    }

    private Events() {
    }
}
