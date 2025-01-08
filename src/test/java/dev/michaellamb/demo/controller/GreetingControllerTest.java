package dev.michaellamb.demo.controller;

import dev.michaellamb.demo.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class GreetingControllerTest extends BaseControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private GreetingController controller;

    @Override
    protected Object getController() {
        return controller;
    }

    @BeforeEach
    public void setUpMockMvc() {
        super.setUpMockMvc();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Should return greeting with provided name")
    void testGreeting() throws Exception {
        mockMvc.perform(get("/greeting")
                        .param("name", TestConstants.ANY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content").value("Hello, " + TestConstants.ANY + "!"));
    }

    @Test
    @DisplayName("Should return default greeting when name is empty")
    void testGreetingWithEmptyName() throws Exception {
        mockMvc.perform(get("/greeting")
                        .param("name", TestConstants.EMPTY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content").value("Hello, World!"));
    }

    @Test
    @DisplayName("Should return hello message with provided name")
    void testHello() throws Exception {
        mockMvc.perform(get("/hello")
                        .param("name", TestConstants.ANY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Hello " + TestConstants.ANY + "!"));
    }

    @Test
    @DisplayName("Should return default hello message when name is empty")
    void testHelloWithEmptyName() throws Exception {
        mockMvc.perform(get("/hello")
                        .param("name", TestConstants.EMPTY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World!"));
    }
}
