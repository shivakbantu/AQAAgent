package org.example.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public final class ConfigLoader {
    private static final String DEFAULT_ENV = "local";
    private static volatile Config cached;

    private ConfigLoader() {
    }

    public static Config load() {
        if (cached != null) {
            return cached;
        }
        synchronized (ConfigLoader.class) {
            if (cached == null) {
                cached = loadInternal();
            }
        }
        return cached;
    }

    public static void clearCache() {
        cached = null;
    }

    private static Config loadInternal() {
        String env = System.getProperty("env", DEFAULT_ENV);
        Properties props = new Properties();

        loadFromClasspath(props, "config/config.properties");
        loadFromClasspath(props, "config/config-" + env + ".properties");

        String browser = read("browser", props, "chrome");
        String baseUrl = read("app.url", props, "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        long explicitWaitSeconds = Long.parseLong(read("wait.explicitSeconds", props, "10"));
        long pageLoadTimeoutSeconds = Long.parseLong(read("timeout.pageLoadSeconds", props, "30"));
        String testDataSource = read("testdata.login", props, "testdata/login.csv");

        return new Config(env, browser, baseUrl, explicitWaitSeconds, pageLoadTimeoutSeconds, testDataSource);
    }

    private static void loadFromClasspath(Properties props, String path) {
        try (InputStream is = ConfigLoader.class.getClassLoader().getResourceAsStream(path)) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed loading config from classpath: " + path, e);
        }
    }

    private static String read(String key, Properties props, String defaultValue) {
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) {
            return sys;
        }
        String val = props.getProperty(key);
        if (val != null && !val.isBlank()) {
            return val;
        }
        return Objects.requireNonNullElse(defaultValue, "");
    }
}
