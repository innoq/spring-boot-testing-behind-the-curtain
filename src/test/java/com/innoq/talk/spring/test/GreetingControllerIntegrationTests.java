package com.innoq.talk.spring.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerIntegrationTests {

    @Autowired
    MockMvcTester mockMvc;

    @Test
    void index_shouldReturnResponse() {
        mockMvc.get()
                .uri("/")
                .param("name", "Isabel")
                .assertThat()
                .hasStatusOk()
                .bodyJson()
                .isEqualTo("""
                        {
                            name: "Isabel",
                            greeting: "Hello, Isabel!"
                        }
                        """);
    }
}