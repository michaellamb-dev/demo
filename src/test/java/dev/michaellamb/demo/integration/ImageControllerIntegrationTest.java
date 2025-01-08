package dev.michaellamb.demo.integration;

import dev.michaellamb.demo.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerIntegrationTest extends BaseIntegrationTest {

    private static final String VALID_SVG_URI = "https://sampleweb/smile.svg";

    @Test
    @DisplayName("Should convert SVG to JPG through the entire stack")
    void testSvgToJpgIntegration() throws Exception {
        mockMvc.perform(get("/svg-to-jpg")
                        .param("svgUri", VALID_SVG_URI)
                        .accept(MediaType.IMAGE_JPEG))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG));
    }

    @Test
    @DisplayName("Should convert SVG to PNG through the entire stack")
    void testSvgToPngIntegration() throws Exception {
        mockMvc.perform(get("/svg-to-png")
                        .param("svgUri", VALID_SVG_URI)
                        .accept(MediaType.IMAGE_PNG))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG));
    }

    @Test
    @DisplayName("Should handle invalid SVG URI in integration")
    void testInvalidSvgUriIntegration() throws Exception {
        mockMvc.perform(get("/svg-to-jpg")
                        .param("svgUri", TestConstants.INVALID))
                .andExpect(status().isInternalServerError());
    }
}
