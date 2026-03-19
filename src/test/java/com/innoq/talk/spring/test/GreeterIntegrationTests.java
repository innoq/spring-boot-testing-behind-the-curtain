package com.innoq.talk.spring.test;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GreeterIntegrationTests {

    @Test
    void greet_shouldGreetGivenPerson() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().getPropertySources()
                .addFirst(new MapPropertySource("test", Map.of("greeting", "Hi %s!")));
        ctx.scan(Application.class.getPackageName());
        ctx.refresh();

        var greeter = ctx.getBean(Greeter.class);

        var greeting = greeter.greet("all");

        assertThat(greeting)
                .isEqualTo("Hi all!");
    }
}