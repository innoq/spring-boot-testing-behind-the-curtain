package com.innoq.talk.spring.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MySpringExtension.class)
class GreeterTests {

    @Autowired
    Greeter greeter;

    @Test
    void greet_shouldGreetGivenPerson() {
        var greeting = greeter.greet("Alle");

        assertThat(greeting).isEqualTo("Moin Alle.");
    }
}