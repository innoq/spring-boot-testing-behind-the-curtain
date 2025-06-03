package com.innoq.talk.spring.test;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WebMvcTest
class GreetingControllerSliceTests {

    @Autowired
    MockMvcTester mockMvc;

    @MockitoBean
    Greeter greeter;

    @Test
    void index_shouldReturnResponse() {
        Mockito.when(greeter.greet("Isabel")).thenReturn("Hello, Isabel!");

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