package org.example.core;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        Config config = ConfigLoader.load();

        DriverManager.setDriver(DriverFactory.createDriver(config.browser()));
        driver = DriverManager.getDriver();

        wait = new WebDriverWait(driver, Duration.ofSeconds(config.explicitWaitSeconds()));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
