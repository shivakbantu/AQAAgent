package org.example.pages.factory;

import org.example.pages.DashboardPage;
import org.example.pages.OrangeHrmLoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
// Factory used by: TC_002 (TestRail Case ID: 149)

 * Factory pattern for page object creation.
 */
public final class PageObjectFactory {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public PageObjectFactory(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public OrangeHrmLoginPage loginPage() {
        return new OrangeHrmLoginPage(driver, wait);
    }

    public DashboardPage dashboardPage() {
        return new DashboardPage(driver, wait);
    }
}
