package com.sarangchurch.follower.aceeptance;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class DataLoaderBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final DataLoader dataLoader;

    public DataLoaderBootstrap(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
          dataLoader.loadData();
    }
}
