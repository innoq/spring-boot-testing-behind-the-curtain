package com.innoq.talk.spring.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MySpringExtension.class)
@MySpringExtension.MyTestProperty(key = "greeting", value = "Hola %s.")
class GreeterIntegration2Tests {

    @Autowired
    Greeter greeter;

    @Test
    void greet_shouldReturnGreeting() {
        var greeteing = greeter.greet("Michael");
        assertThat(greeteing).isEqualTo("Hola Michael.");
    }

    @Test
    void greet_shouldReturnGreeting2() {
        var greeteing = greeter.greet("Michael");
        assertThat(greeteing).isEqualTo("Hola Michael.");
    }
}