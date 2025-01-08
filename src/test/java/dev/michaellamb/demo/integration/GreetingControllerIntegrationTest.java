package dev.michaellamb.demo.integration;

import dev.michaellamb.demo.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GreetingControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Should return greeting with name through the entire stack")
    void testGreetingIntegration() throws Exception {
        mockMvc.perform(get("/greeting")
                        .param("name", TestConstants.ANY)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").value("Hello, " + TestConstants.ANY + "!"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("Should return hello message through the entire stack")
    void testHelloIntegration() throws Exception {
        mockMvc.perform(get("/hello")
                        .param("name", TestConstants.ANY)
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
                .andExpect(content().string("Hello " + TestConstants.ANY + "!"));
    }
}
