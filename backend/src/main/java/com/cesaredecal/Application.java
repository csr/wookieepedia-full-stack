package com.cesaredecal;

import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.event.ApplicationStartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }

    @EventListener
    void onStartup(ApplicationStartupEvent event) {
    }
}