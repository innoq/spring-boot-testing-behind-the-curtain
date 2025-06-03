package com.innoq.talk.spring.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mock.env.MockPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MySpringExtension.class)
@MySpringExtension.MyTestProperty(key = "greeting", value = "Hola %s.")
class GreeterIntegrationTests {

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