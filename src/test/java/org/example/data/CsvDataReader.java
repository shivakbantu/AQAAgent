package org.example.data;

import com.opencsv.CSVReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class CsvDataReader {
    private CsvDataReader() {
    }

    public static List<Map<String, String>> readAsMaps(String classpathResource) {
        try (InputStream is = CsvDataReader.class.getClassLoader().getResourceAsStream(classpathResource)) {
            if (is == null) {
                throw new IllegalArgumentException("CSV resource not found on classpath: " + classpathResource);
            }

            try (CSVReader reader = new CSVReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                List<String[]> rows = reader.readAll();
                if (rows.isEmpty()) {
                    return List.of();
                }

                String[] headers = rows.getFirst();
                List<Map<String, String>> out = new ArrayList<>();
                for (int i = 1; i < rows.size(); i++) {
                    String[] values = rows.get(i);
                    Map<String, String> map = new LinkedHashMap<>();
                    for (int h = 0; h < headers.length; h++) {
                        String key = headers[h];
                        String val = h < values.length ? values[h] : "";
                        map.put(key, val);
                    }
                    out.add(map);
                }
                return out;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read CSV test data: " + classpathResource, e);
        }
    }
}

