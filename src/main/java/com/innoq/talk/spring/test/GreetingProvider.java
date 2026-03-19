package com.innoq.talk.spring.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class GreetingProvider {

    private final String greeting;

    public GreetingProvider(@Value("${greeting}") String greeting) {
        this.greeting = greeting;
    }

    public String get() {
        return greeting;
    }
}
