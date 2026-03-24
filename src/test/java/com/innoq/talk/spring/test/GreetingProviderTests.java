package com.innoq.talk.spring.test;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GreetingProviderTests {

    @Test
    void get_shouldReturnGreetingTemplate() {
        var provider = new GreetingProvider("Hi %s.");

        var greetingTemplate = provider.get();

        assertThat(greetingTemplate).isEqualTo("Hi %s.");
    }
}