package com.innoq.talk.spring.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "greeting=Hi %s!")
class GreeterIntegrationTests {

    @Autowired
    Greeter greeter;

    @Test
    void greet_shouldGreetGivenPerson() {
        var greeting = greeter.greet("all");

        assertThat(greeting)
                .isEqualTo("Hi all!");
    }

    @Configuration
    @ComponentScan(basePackageClasses = Application.class)
    static class TestConfig {
    }
}