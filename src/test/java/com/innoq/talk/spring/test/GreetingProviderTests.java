package com.innoq.talk.spring.test;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GreetingProviderTests {

    @Test
    void get_shouldReturnConfiguredTemplate() {
        GreetingProvider provider = new GreetingProvider();
        ReflectionTestUtils.setField(provider, "greeting", "Moin %s.");

        String template = provider.get();

        assertThat(template).isEqualTo("Moin %s.");

    }
}