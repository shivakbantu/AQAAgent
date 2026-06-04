package org.example.data.builders;

import org.example.core.ConfigLoader;

/**
 * Builder facade for constructing LoginCredentials.
 *
 * Sensitive data handling (highest priority first):
 * 1) JVM system properties: -Dapp.username / -Dapp.password
 * 2) Environment variables: ORANGEHRM_USERNAME / ORANGEHRM_PASSWORD
 * 3) Config file (local fallback): config/config.properties -> app.username / app.password
 */
public final class LoginCredentialsBuilder {

    private LoginCredentialsBuilder() {
        // utility
    }

    public static LoginCredentials fromEnvOrSystemProps() {
        // 1) System properties
        String user = firstNonBlank(System.getProperty("app.username"), System.getenv("ORANGEHRM_USERNAME"));
        String pass = firstNonBlank(System.getProperty("app.password"), System.getenv("ORANGEHRM_PASSWORD"));

        // 3) Config file fallback (load config so it gets cached elsewhere; then read raw properties)
        if (isBlank(user) || isBlank(pass)) {
            // Ensure any config side effects/caching happens as per framework convention
            ConfigLoader.load();

            java.util.Properties props = new java.util.Properties();
            try (java.io.InputStream is = LoginCredentialsBuilder.class.getClassLoader()
                    .getResourceAsStream("config/config.properties")) {
                if (is != null) {
                    props.load(is);
                }
            } catch (java.io.IOException e) {
                throw new RuntimeException("Failed loading config/config.properties for credential fallback", e);
            }

            user = firstNonBlank(user, props.getProperty("app.username"));
            pass = firstNonBlank(pass, props.getProperty("app.password"));
        }

        if (isBlank(user) || isBlank(pass)) {
            throw new IllegalStateException(
                    "Missing credentials. Set ORANGEHRM_USERNAME / ORANGEHRM_PASSWORD env vars "
                            + "or pass -Dapp.username / -Dapp.password system properties."
            );
        }

        return LoginCredentials.builder()
                .withUsernameFromEnv("ORANGEHRM_USERNAME", user)
                .withPasswordFromEnv("ORANGEHRM_PASSWORD", pass)
                .build();
    }

    private static String firstNonBlank(String... values) {
        for (String v : values) {
            if (v != null && !v.isBlank()) {
                return v;
            }
        }
        return "";
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}