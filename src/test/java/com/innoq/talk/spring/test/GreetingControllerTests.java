package com.innoq.talk.spring.test;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GreetingControllerTests {

    @Test
    void index_shouldReturnResponse() {
        var greeter = mock(Greeter.class);
        var controller = new GreetingController(greeter);

        when(greeter.greet("Michael")).thenReturn("Hello, Michael!");

        var response = controller.index("Michael");

        assertThat(response)
                .hasSize(2)
                .contains(
                        Map.entry("name", "Michael"),
                        Map.entry("greeting", "Hello, Michael!"));
    }
}