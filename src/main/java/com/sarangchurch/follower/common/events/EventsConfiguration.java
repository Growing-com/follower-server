package com.sarangchurch.follower.common.events;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EventsConfiguration implements InitializingBean {

    private final ApplicationContext publisher;

    @Override
    public void afterPropertiesSet() throws Exception {
        Events.setPublisher(publisher);
    }
}
