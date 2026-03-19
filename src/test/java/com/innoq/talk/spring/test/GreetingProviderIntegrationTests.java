package com.innoq.talk.spring.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DemoSpringExtension.class)
class GreetingProviderIntegrationTests {

    @Autowired
    GreetingProvider greetingProvider;

    @Test
    void get_shouldReturnTemplate() {
        var template = greetingProvider.get();

        assertThat(template)
                .isEqualTo("Hi %s!");
    }
}