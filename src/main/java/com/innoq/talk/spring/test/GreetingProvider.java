package com.innoq.talk.spring.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class GreetingProvider {

    @Value("${greeting}")
    private String greeting;

    public String get() {
        return greeting;
    }
}
