package com.innoq.talk.spring.test;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
class Greeter {

    private final GreetingProvider greetingProvider;

    public Greeter(GreetingProvider greetingProvider) {
        this.greetingProvider = greetingProvider;
    }

    public String greet(String name) {
        var greeting = greetingProvider.get();
        return greeting.formatted(name);
    }

    @PostConstruct
    void onInit() throws Exception {
        System.out.println("Greeter.onInit");
        TimeUnit.SECONDS.sleep(3);
    }
}
