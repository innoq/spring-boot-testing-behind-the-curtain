package com.innoq.talk.spring.test;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mock.env.MockPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

class GreeterTests {

    @Test
    void greet_shouldGreetGivenPerson() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().getPropertySources().addFirst(new MockPropertySource().withProperty("greeting", "Moin %s."));
        ctx.scan(Application.class.getPackageName());
        ctx.refresh();

        var greeter = ctx.getBean(Greeter.class);

        var greeting = greeter.greet("Alle");

        assertThat(greeting).isEqualTo("Moin Alle.");
    }
}