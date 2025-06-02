package com.innoq.talk.spring.test;

import com.innoq.talk.spring.test.MySpringExtension.MyTestProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MySpringExtension.class)
@MyTestProperty(key = "greeting", value = "Hello, %s!")
class Greeter2Tests {

    @Autowired
    Greeter greeter;

    @Test
    void greet_shouldGreetGivenPerson() {
        var greeting = greeter.greet("Alle");

        assertThat(greeting).isEqualTo("Hello, Alle!");
    }

    @Test
    void greet_shouldGreetGivenPerson2() {
        var greeting = greeter.greet("Michael");

        assertThat(greeting).isEqualTo("Hello, Michael!");
    }
}