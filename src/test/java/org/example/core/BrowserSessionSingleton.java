package org.example.core;

import org.openqa.selenium.WebDriver;

/**
 * Singleton wrapper for accessing the current thread's WebDriver.
 * Uses existing framework DriverManager + DriverFactory underneath.
 */
public final class BrowserSessionSingleton {
    private static volatile BrowserSessionSingleton instance;

    private BrowserSessionSingleton() {
    }

    public static BrowserSessionSingleton getInstance() {
        if (instance == null) {
            synchronized (BrowserSessionSingleton.class) {
                if (instance == null) {
                    instance = new BrowserSessionSingleton();
                }
            }
        }
        return instance;
    }

    public WebDriver driver() {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized for current thread. Ensure setup ran.");
        }
        return driver;
    }
}
