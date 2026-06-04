package org.example.data.builders;

/**
 * Builder facade for constructing LoginCredentials.
 *
 * Sensitive data handling:
 * - Preferred: ORANGEHRM_USERNAME / ORANGEHRM_PASSWORD environment variables
 * - Alternative: -Dapp.username / -Dapp.password JVM system properties
 */
public final class LoginCredentialsBuilder {

    private LoginCredentialsBuilder() {
        // utility
    }

    public static LoginCredentials fromEnvOrSystemProps() {
        String user = firstNonBlank(System.getProperty("app.username"), System.getenv("ORANGEHRM_USERNAME"));
        String pass = firstNonBlank(System.getProperty("app.password"), System.getenv("ORANGEHRM_PASSWORD"));

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

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
    }
}