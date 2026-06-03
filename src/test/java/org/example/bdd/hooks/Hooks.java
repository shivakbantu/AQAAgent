package org.example.bdd.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.example.core.Config;
import org.example.core.ConfigLoader;
import org.example.core.DriverFactory;
import org.example.core.DriverManager;
import org.example.core.ElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Hooks {

    @Before
    public void beforeScenario() {
        Config config = ConfigLoader.load();
        DriverManager.setDriver(DriverFactory.createDriver(config.browser()));

        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            driver.manage().window().maximize();
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                WebDriver driver = DriverManager.getDriver();
                if (driver != null) {
                    Config config = ConfigLoader.load();
                    WebDriverWait wait = ElementActions.wait(driver, config.explicitWaitSeconds());
                    ElementActions actions = new ElementActions(driver, wait);
                    scenario.attach(actions.screenshotBytes(), "image/png", "failure-screenshot");
                }
            }
        } finally {
            DriverManager.quitDriver();
        }
    }
}

