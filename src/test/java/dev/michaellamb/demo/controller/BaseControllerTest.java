package dev.michaellamb.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public abstract class BaseControllerTest {
    protected MockMvc mockMvc;

    protected abstract Object getController();

    @BeforeEach
    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(getController()).build();
    }
}
