package com.innoq.talk.spring.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerIntegrationTests {

    @Autowired
    MockMvcTester mockMvc;

    @Test
    void index_shouldReturnCorrectResponse() {
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