package dev.michaellamb.demo.integration;

import dev.michaellamb.demo.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GameStatusControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Should handle game status request through the entire stack")
    void testGameStatusIntegration() throws Exception {
        mockMvc.perform(get("/steam/valheim")
                        .param("address", TestConstants.ANY)
                        .param("key", TestConstants.ANY)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.up").exists());
    }

    @Test
    @DisplayName("Should handle invalid game server address in integration")
    void testInvalidAddressIntegration() throws Exception {
        mockMvc.perform(get("/steam/valheim")
                        .param("address", TestConstants.INVALID)
                        .param("key", TestConstants.ANY))
                .andExpect(status().isInternalServerError());
    }
}
