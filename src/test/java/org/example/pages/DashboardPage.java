package org.example.pages;

import org.example.core.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * POM for OrangeHRM Dashboard page (selectors verified via Playwright MCP).
// Used by: TC_002 (TestRail Case ID: 149)

 */
public class DashboardPage {

    // Playwright-confirmed selector; header text should equal "Dashboard"
    private static final By DASHBOARD_HEADER = By.cssSelector("h6.oxd-topbar-header-breadcrumb-module");

    private final WebDriver driver;
    private final ElementActions actions;

    public DashboardPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.actions = new ElementActions(driver, wait);
    }

    public String headerText() {
        return actions.text(DASHBOARD_HEADER).trim();
    }

    /**
     * Alias for consistency across tests.
     */
    public String getModuleHeaderText() {
        return headerText();
    }

    public boolean isAt() {
        try {
            return "Dashboard".equals(headerText());
        } catch (Exception ignored) {
            return false;
        }
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * URL-based assertion for successful redirect.
     */
    public boolean isAtDashboard() {
        return currentUrl().contains("/web/index.php/dashboard/index");
    }
}
