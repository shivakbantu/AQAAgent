package org.example.data.models;

/**
 * Immutable login credentials model (Builder pattern).
 */
public final class LoginCredentials {
    private final String username;
    private final String password;

    private LoginCredentials(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
    }

    public String username() { return username; }
    public String password() { return password; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String username;
        private String password;

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public LoginCredentials build() {
            if (username == null || username.isBlank()) {
                throw new IllegalArgumentException("username must be provided");
            }
            if (password == null || password.isBlank()) {
                throw new IllegalArgumentException("password must be provided");
            }
            return new LoginCredentials(this);
        }
    }
}
