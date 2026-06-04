package org.example.core;

import org.openqa.selenium.WebDriver;

/**
 * Singleton session manager (single instance).
 * Note: Driver instances remain ThreadLocal via DriverManager to keep parallel safety.
 */
public final class BrowserSession {

    private static final BrowserSession INSTANCE = new BrowserSession();

    private BrowserSession() {}

    public static BrowserSession getInstance() {
        return INSTANCE;
    }

    public WebDriver start() {
        Config config = ConfigLoader.load();
        if (DriverManager.getDriver() == null) {
            DriverManager.setDriver(DriverFactory.createDriver(config.browser()));
        }
        return DriverManager.getDriver();
    }

    public void stop() {
        DriverManager.quitDriver();
    }
}
