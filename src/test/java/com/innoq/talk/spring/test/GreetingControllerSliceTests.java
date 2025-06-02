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
    void index_shouldReturnCorrectResponse() {
        Mockito.when(greeter.greet("Michael"))
                        .thenReturn("Moin Michael.");

        mockMvc.get()
                .uri("/")
                .param("name", "Michael")
                .assertThat()
                .hasStatusOk()
                .bodyJson()
                .isEqualTo("""
                        {
                            name: "Michael",
                            greeting: "Moin Michael."
                        }
                        """);
    }
}