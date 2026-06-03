package org.example.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public final class JsonDataReader {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonDataReader() {
    }

    public static List<Map<String, String>> readAsMaps(String classpathResource) {
        try (InputStream is = JsonDataReader.class.getClassLoader().getResourceAsStream(classpathResource)) {
            if (is == null) {
                throw new IllegalArgumentException("JSON resource not found on classpath: " + classpathResource);
            }
            return MAPPER.readValue(is, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON test data: " + classpathResource, e);
        }
    }
}

