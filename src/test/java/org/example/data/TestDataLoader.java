package org.example.data;

import java.util.List;
import java.util.Map;

public final class TestDataLoader {
    private TestDataLoader() {
    }

    public static List<Map<String, String>> load(String classpathResource) {
        if (classpathResource == null || classpathResource.isBlank()) {
            throw new IllegalArgumentException("test data resource path is blank");
        }

        String lower = classpathResource.toLowerCase();
        if (lower.endsWith(".csv")) {
            return CsvDataReader.readAsMaps(classpathResource);
        }
        if (lower.endsWith(".json")) {
            return JsonDataReader.readAsMaps(classpathResource);
        }
        if (lower.endsWith(".xlsx")) {
            return ExcelDataReader.readSheetAsMaps(classpathResource, "login");
        }

        throw new IllegalArgumentException("Unsupported test data format: " + classpathResource);
    }
}

