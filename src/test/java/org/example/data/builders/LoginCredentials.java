package org.example.data.builders;

import java.util.Objects;

// Test data builder used by TC_002 (TestRail Case ID: 149)

/**
 * Builder pattern for login test data.
 */
public final class LoginCredentials {
    private final String username;
    private final String password;

    private LoginCredentials(Builder builder) {
        this.username = Objects.requireNonNull(builder.username, "username must not be null");
        this.password = Objects.requireNonNull(builder.password, "password must not be null");
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String username;
        private String password;

        public Builder withUsernameFromEnv(String envKey, String fallback) {
            String val = System.getenv(envKey);
            this.username = (val == null || val.isBlank()) ? fallback : val;
            return this;
        }

        public Builder withPasswordFromEnv(String envKey, String fallback) {
            String val = System.getenv(envKey);
            this.password = (val == null || val.isBlank()) ? fallback : val;
            return this;
        }

        public LoginCredentials build() {
            return new LoginCredentials(this);
        }
    }
}
