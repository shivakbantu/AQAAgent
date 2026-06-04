package org.example.pages.factory;

import org.example.core.ElementActions;
import org.example.pages.DashboardPageV2;
import org.example.pages.OrangeHrmLoginPageV2;
import org.openqa.selenium.WebDriver;

/**
 * Factory pattern for Page Object creation.
 */
public final class PageObjectFactoryV2 {

    private final WebDriver driver;
    private final ElementActions actions;

    public PageObjectFactoryV2(WebDriver driver, ElementActions actions) {
        this.driver = driver;
        this.actions = actions;
    }

    public OrangeHrmLoginPageV2 loginPage() {
        return new OrangeHrmLoginPageV2(driver, actions);
    }

    public DashboardPageV2 dashboardPage() {
        return new DashboardPageV2(driver, actions);
    }
}
