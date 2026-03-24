package com.innoq.talk.spring.test;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
class GreetingProvider {

    private final String greeting;

    public GreetingProvider(@Value("${greeting}") String greeting) {
        this.greeting = greeting;
    }

    public String get() {
        return greeting;
    }

    @PostConstruct
    public void onInit() throws InterruptedException {
        System.out.println("GreetingProvider.onInit");
        TimeUnit.SECONDS.sleep(3);
    }
}
