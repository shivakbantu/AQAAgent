package org.example.pages;

import org.example.core.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * OrangeHRM Dashboard Page (POM).
 */
public class DashboardPageV2 {

    private final WebDriver driver;
    private final ElementActions actions;

    // Playwright-confirmed: dashboard header is the first h6 with text 'Dashboard'
    private final By dashboardHeader = By.cssSelector("h6");

    public DashboardPageV2(WebDriver driver, ElementActions actions) {
        this.driver = driver;
        this.actions = actions;
    }

    public String headerText() {
        return actions.text(dashboardHeader).trim();
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }
}
