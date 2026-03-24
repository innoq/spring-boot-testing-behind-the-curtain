package com.innoq.talk.spring.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.mockito.Mockito.when;

@WebMvcTest
class GreetingControllerIntegrationSliceTests {

    @Autowired
    MockMvcTester mockMvc;

    @MockitoBean
    Greeter greeter;

    @Test
    void index_shouldReturnCorrectResponse() {
        when(greeter.greet("Michael"))
                .thenReturn("Hello, Michael!");

        mockMvc.get()
                .uri("/")
                .param("name", "Michael")
                .assertThat()
                .hasStatusOk()
                .bodyJson()
                .isEqualTo("""
                        {
                            name: "Michael",
                            greeting: "Hello, Michael!"
                        }
                        """);
    }
}