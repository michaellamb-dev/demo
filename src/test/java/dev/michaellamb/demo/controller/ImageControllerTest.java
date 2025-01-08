package dev.michaellamb.demo.controller;

import dev.michaellamb.demo.TestConstants;
import dev.michaellamb.demo.agent.ImageAgent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest extends BaseControllerTest {

    private static final String VALID_SVG_URI = "https://sampleweb/smile.svg";
    private static final byte[] MOCK_IMAGE_DATA = "mock image data".getBytes();

    @Mock
    private ImageAgent imageAgent;

    @InjectMocks
    private ImageController controller;

    @Override
    protected Object getController() {
        return controller;
    }

    @Test
    @DisplayName("Should convert SVG to JPG successfully")
    void testGetSvgToJpg() throws Exception {
        when(imageAgent.exchangeSvgUriForJpeg(anyString())).thenReturn(MOCK_IMAGE_DATA);

        mockMvc.perform(get("/svg-to-jpg")
                        .param("svgUri", VALID_SVG_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(MOCK_IMAGE_DATA));
    }

    @Test
    @DisplayName("Should return bad request when SVG URI is empty for JPG conversion")
    void testGetSvgToJpgWithEmptyUri() throws Exception {
        mockMvc.perform(get("/svg-to-jpg")
                        .param("svgUri", TestConstants.EMPTY))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle SVG to JPG conversion failure")
    void testGetSvgToJpgWithConversionError() throws Exception {
        when(imageAgent.exchangeSvgUriForJpeg(anyString())).thenThrow(new RuntimeException("Conversion failed"));

        mockMvc.perform(get("/svg-to-jpg")
                        .param("svgUri", VALID_SVG_URI))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Should convert SVG to PNG successfully")
    void testGetSvgToPng() throws Exception {
        when(imageAgent.exchangeSvgUriForPng(anyString())).thenReturn(MOCK_IMAGE_DATA);

        mockMvc.perform(get("/svg-to-png")
                        .param("svgUri", VALID_SVG_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andExpect(content().bytes(MOCK_IMAGE_DATA));
    }

    @Test
    @DisplayName("Should return bad request when SVG URI is empty for PNG conversion")
    void testGetSvgToPngWithEmptyUri() throws Exception {
        mockMvc.perform(get("/svg-to-png")
                        .param("svgUri", TestConstants.EMPTY))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle SVG to PNG conversion failure")
    void testGetSvgToPngWithConversionError() throws Exception {
        when(imageAgent.exchangeSvgUriForPng(anyString())).thenThrow(new RuntimeException("Conversion failed"));

        mockMvc.perform(get("/svg-to-png")
                        .param("svgUri", VALID_SVG_URI))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}
