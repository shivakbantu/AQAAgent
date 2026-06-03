package org.example.keywords;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import org.openqa.selenium.By;

public final class ObjectRepository {
    private final Properties props;

    private ObjectRepository(Properties props) {
        this.props = props;
    }

    public static ObjectRepository load(String classpathResource) {
        Properties p = new Properties();
        try (InputStream is = ObjectRepository.class.getClassLoader().getResourceAsStream(classpathResource)) {
            if (is == null) {
                throw new IllegalArgumentException("Object repository not found: " + classpathResource);
            }
            p.load(is);
            return new ObjectRepository(p);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load object repository: " + classpathResource, e);
        }
    }

    public By getLocator(String key) {
        String raw = props.getProperty(key);
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("Missing locator for key: " + key);
        }

        String[] parts = raw.split("=", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid locator format for key: " + key + " value=" + raw);
        }

        String type = parts[0].trim().toLowerCase();
        String value = parts[1].trim();

        return switch (type) {
            case "id" -> By.id(value);
            case "name" -> By.name(value);
            case "css" -> By.cssSelector(value);
            case "xpath" -> By.xpath(value);
            default -> throw new IllegalArgumentException("Unsupported locator type: " + type + " for key: " + key);
        };
    }

    public Map<String, String> asMap() {
        return props.entrySet().stream()
                .collect(Collectors.toMap(e -> String.valueOf(e.getKey()), e -> String.valueOf(e.getValue())));
    }
}
