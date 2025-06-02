package com.innoq.talk.spring.test;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GreetingControllerTests {

    @Test
    void index_shouldReturnResponse() {
        var greeter = Mockito.mock(Greeter.class);
        GreetingController controller = new GreetingController(greeter);

        Mockito.when(greeter.greet("Michael")).thenReturn("Hello, Michael!");

        var response = controller.index("Michael");

        assertThat(response)
                .hasSize(2)
                .contains(
                        Map.entry("name", "Michael"),
                        Map.entry("greeting", "Hello, Michael!"));
    }
}