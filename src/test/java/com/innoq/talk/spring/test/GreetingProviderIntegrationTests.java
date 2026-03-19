package com.innoq.talk.spring.test;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GreetingProviderIntegrationTests {

    @Test
    void get_shouldReturnTemplate() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().getPropertySources()
                .addFirst(new MapPropertySource("test", Map.of("greeting", "Hi %s!")));
        ctx.scan(Application.class.getPackageName());
        ctx.refresh();

        var greetingProvider = ctx.getBean(GreetingProvider.class);

        var template = greetingProvider.get();

        assertThat(template)
                .isEqualTo("Hi %s!");
    }
}