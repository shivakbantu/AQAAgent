package org.example.core;

public record Config(
        String env,
        String browser,
        String baseUrl,
        long explicitWaitSeconds,
        long pageLoadTimeoutSeconds,
        String testDataSource
) {
}

