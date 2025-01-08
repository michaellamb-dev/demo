package dev.michaellamb.demo.controller;

import dev.michaellamb.demo.TestConstants;
import dev.michaellamb.demo.agent.GameStatusAgent;
import dev.michaellamb.demo.model.GameStatusResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class GameStatusControllerTest extends BaseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GameStatusAgent gameStatusAgent;

    @InjectMocks
    private GameStatusController controller;

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
    @DisplayName("Should return game status when server is up")
    void testGetValheimStatusWhenUp() throws Exception {
        GameStatusResponse gameStatusResponse = new GameStatusResponse();
        gameStatusResponse.setUp(true);
        when(gameStatusAgent.getValheimInstanceStatus(anyString(), anyString())).thenReturn(gameStatusResponse);

        mockMvc.perform(get("/steam/valheim")
                        .param("address", TestConstants.ANY)
                        .param("key", TestConstants.ANY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.up").value(true));
    }

    @Test
    @DisplayName("Should return game status when server is down")
    void testGetValheimStatusWhenDown() throws Exception {
        GameStatusResponse gameStatusResponse = new GameStatusResponse();
        gameStatusResponse.setUp(false);
        when(gameStatusAgent.getValheimInstanceStatus(anyString(), anyString())).thenReturn(gameStatusResponse);

        mockMvc.perform(get("/steam/valheim")
                        .param("address", TestConstants.ANY)
                        .param("key", TestConstants.ANY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.up").value(false));
    }

    @Test
    @DisplayName("Should return bad request when address is empty")
    void testGetValheimStatusWithEmptyAddress() throws Exception {
        mockMvc.perform(get("/steam/valheim")
                        .param("address", TestConstants.EMPTY)
                        .param("key", TestConstants.ANY))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when key is empty")
    void testGetValheimStatusWithEmptyKey() throws Exception {
        mockMvc.perform(get("/steam/valheim")
                        .param("address", TestConstants.ANY)
                        .param("key", TestConstants.EMPTY))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
