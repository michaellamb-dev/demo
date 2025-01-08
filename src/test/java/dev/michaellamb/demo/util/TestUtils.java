package dev.michaellamb.demo.util;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class TestUtils {
    private TestUtils() {
        // Prevent instantiation
    }

    public static MockMultipartFile createMockImageFile(String filename, String contentType) throws IOException {
        Path resourceDirectory = Paths.get("src", "test", "resources", "images", filename);
        byte[] content = Files.readAllBytes(resourceDirectory);
        return new MockMultipartFile(
            "file",
            filename,
            contentType,
            content
        );
    }

    public static MockMultipartFile createEmptyFile(String filename) {
        return new MockMultipartFile(
            "file",
            filename,
            MediaType.APPLICATION_OCTET_STREAM_VALUE,
            new byte[0]
        );
    }

    public static byte[] readTestResource(String resourcePath) throws IOException {
        Path path = Paths.get("src", "test", "resources", resourcePath);
        return Files.readAllBytes(path);
    }
}
