package com.innoq.talk.spring.test;

import com.innoq.talk.spring.test.DemoSpringExtension.TestProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DemoSpringExtension.class)
@TestProperty(key = "greeting", value = "Hi %s!")
class GreeterIntegrationTests {

    @Autowired
    Greeter greeter;

    @Test
    void greet_shouldGreetGivenPerson() {
        var greeting = greeter.greet("all");

        assertThat(greeting)
                .isEqualTo("Hi all!");
    }
}