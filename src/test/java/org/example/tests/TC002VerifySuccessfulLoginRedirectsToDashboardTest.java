package org.example.tests;

import org.example.core.BrowserSession;
import org.example.core.Config;
import org.example.core.ConfigLoader;
import org.example.core.ElementActions;
import org.example.data.builders.LoginCredentials;
import org.example.pages.DashboardPage;
import org.example.pages.OrangeHrmLoginPage;
import org.example.pages.factory.PageObjectFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

/**
 * TestRail: TC_002 (Case ID: 149)
 */
public class TC002VerifySuccessfulLoginRedirectsToDashboardTest {

    private final BrowserSession session = BrowserSession.getInstance(); // Singleton
    private WebDriver driver;
    private WebDriverWait wait;
    private Config config;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        config = ConfigLoader.load();
        driver = session.start();
        wait = new WebDriverWait(driver, Duration.ofSeconds(config.explicitWaitSeconds()));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        session.stop();
    }

    @Test
    public void verifySuccessfulLoginWithValidCredentialsRedirectsToDashboard() throws Exception {
        // Builder: test data construction (env-driven)
        LoginCredentials creds = LoginCredentials.builder()
                .withUsernameFromEnv("ORANGEHRM_USERNAME", "Admin")
                .withPasswordFromEnv("ORANGEHRM_PASSWORD", "admin123")
                .build();

        PageObjectFactory pages = new PageObjectFactory(driver, wait); // Factory
        OrangeHrmLoginPage loginPage = pages.loginPage();
        DashboardPage dashboardPage = pages.dashboardPage();

        try {
            // Precondition: User is on OrangeHRM login page
            loginPage.open(config.baseUrl());

            // Step 1: Enter Admin in Username.
            loginPage.enterUsername(creds.username());

            // Step 2: Enter admin123 in Password.
            loginPage.enterPassword(creds.password());

            // Step 3: Click Login.
            loginPage.clickLogin();

            // Expected: User is redirected to the OrangeHRM dashboard.
            Assert.assertTrue(
                    dashboardPage.currentUrl().contains("/web/index.php/dashboard/index"),
                    "Expected to be redirected to dashboard URL after login"
            );
            Assert.assertEquals(
                    dashboardPage.headerText(),
                    "Dashboard",
                    "User should land on Dashboard after login"
            );

            // Expected: Login page is no longer displayed.
            Assert.assertFalse(
                    dashboardPage.currentUrl().contains("/web/index.php/auth/login"),
                    "Login page URL should no longer be displayed after successful login"
            );
            Assert.assertFalse(
                    loginPage.isLoginFormPresent(),
                    "Login form should not be present after successful login"
            );

        } catch (AssertionError | RuntimeException e) {
            // Error handling: capture screenshot on failure (best-effort)
            try {
                ElementActions actions = new ElementActions(driver, wait);
                byte[] png = actions.screenshotBytes();
                if (png.length > 0) {
                    Path out = Path.of("target", "screenshots", "TC002-login-failure.png");
                    Files.createDirectories(out.getParent());
                    Files.write(out, png);
                }
            } catch (Exception ignored) {
                // keep failure reason intact
            }
            throw e;
        }
    }
}
