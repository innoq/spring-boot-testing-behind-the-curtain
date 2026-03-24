package com.innoq.talk.spring.test;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GreeterTests {

    @Test
    void greet_shouldReturnGreeting() {
        var greetingProvider = mock(GreetingProvider.class);
        var greeter = new Greeter(greetingProvider);

        when(greetingProvider.get()).thenReturn("Hi %s.");

        var greeting = greeter.greet("Michael");

        assertThat(greeting)
                .isEqualTo("Hi Michael.");
    }
}