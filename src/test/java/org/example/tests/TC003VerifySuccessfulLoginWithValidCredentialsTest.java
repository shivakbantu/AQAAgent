package org.example.tests;

import java.time.Duration;
import org.example.core.BrowserSessionSingleton;
import org.example.core.Config;
import org.example.core.ConfigLoader;
import org.example.core.DriverFactory;
import org.example.core.DriverManager;
import org.example.core.ElementActions;
import org.example.data.models.LoginCredentials;
import org.example.pages.DashboardPageV2;
import org.example.pages.OrangeHrmLoginPageV2;
import org.example.pages.factory.PageObjectFactoryV2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * TestRail: TC_003 - Verify successful login with valid credentials (Admin/admin123)
 * Case ID: 221
 *
 * Notes:
 * - Credentials are sourced from environment variables to avoid hardcoding secrets.
 *   Provide:
 *   - ORANGEHRM_USERNAME (default: Admin)
 *   - ORANGEHRM_PASSWORD (default: admin123)
 */
public class TC003VerifySuccessfulLoginWithValidCredentialsTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private ElementActions actions;
    private PageObjectFactoryV2 pages;
    private Config config;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        config = ConfigLoader.load();

        driver = DriverFactory.createDriver(config.browser());
        DriverManager.setDriver(driver);

        wait = new WebDriverWait(driver, Duration.ofSeconds(config.explicitWaitSeconds()));
        actions = new ElementActions(driver, wait);
        pages = new PageObjectFactoryV2(BrowserSessionSingleton.getInstance().driver(), actions);

        // Precondition: user is on login page
        pages.loginPage().open(config.baseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }

    @Test
    public void shouldLoginSuccessfullyWithValidCredentials() {
        OrangeHrmLoginPageV2 loginPage = pages.loginPage();
        DashboardPageV2 dashboardPage = pages.dashboardPage();

        // Builder pattern for test data
        LoginCredentials creds = LoginCredentials.builder()
                .withUsername(envOrDefault("ORANGEHRM_USERNAME", "Admin"))
                .withPassword(envOrDefault("ORANGEHRM_PASSWORD", "admin123"))
                .build();

        // Step 1: Enter Admin in Username.
        loginPage.enterUsername(creds.username());
        Assert.assertTrue(loginPage.isLoginFormDisplayed(),
                "Expected login form to remain displayed after entering username.");

        // Step 2: Enter admin123 in Password.
        loginPage.enterPassword(creds.password());
        Assert.assertTrue(loginPage.isLoginFormDisplayed(),
                "Expected login form to remain displayed after entering password.");

        // Step 3: Click Login.
        loginPage.clickLogin();

        // Expected: User is redirected to the OrangeHRM dashboard and the login page is no longer displayed.
        Assert.assertTrue(dashboardPage.currentUrl().contains("/dashboard/index"),
                "Expected to be redirected to dashboard. Actual URL: " + dashboardPage.currentUrl());
        Assert.assertEquals(dashboardPage.headerText(), "Dashboard",
                "Expected Dashboard header to be displayed.");
        Assert.assertFalse(pages.loginPage().isLoginFormDisplayed(),
                "Expected login page to no longer be displayed after login.");
    }

    private String envOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }
}
