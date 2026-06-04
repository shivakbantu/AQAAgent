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
 * TestRail: TC195 (Case ID: 195)
 * Title: Verify successful login with valid credentials (Admin/admin123)
 */
public class TC195VerifySuccessfulLoginWithValidCredentialsAdminTest {

    // Singleton (required): one shared session instance
    private final BrowserSession session = BrowserSession.getInstance();

    private WebDriver driver;
    private WebDriverWait wait;
    private Config config;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        config = ConfigLoader.load();

        driver = session.start();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.pageLoadTimeoutSeconds()));

        wait = new WebDriverWait(driver, Duration.ofSeconds(config.explicitWaitSeconds()));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        session.stop();
    }

    @Test
    public void verifySuccessfulLoginWithValidCredentialsAdminAdmin123() throws Exception {
        // Builder (required): env-driven sensitive data
        LoginCredentials creds = LoginCredentials.builder()
                .withUsernameFromEnv("ORANGEHRM_USERNAME", "Admin")
                .withPasswordFromEnv("ORANGEHRM_PASSWORD", "admin123")
                .build();

        // Factory (required): page creation
        PageObjectFactory pages = new PageObjectFactory(driver, wait);
        OrangeHrmLoginPage loginPage = pages.loginPage();
        DashboardPage dashboardPage = pages.dashboardPage();

        try {
            // Preconditions: User is on the OrangeHRM login page
            loginPage.open(config.baseUrl());

            // Step 1: Enter `Admin` in Username field.
            loginPage.enterUsername(creds.username());

            // Step 2: Enter `admin123` in Password field.
            loginPage.enterPassword(creds.password());

            // Step 3: Click Login.
            loginPage.clickLogin();

            // Expected Result: User is redirected to the OrangeHRM dashboard page.
            Assert.assertTrue(
                    dashboardPage.currentUrl().contains("/web/index.php/dashboard/index"),
                    "Expected to be redirected to dashboard URL after login"
            );
            Assert.assertEquals(
                    dashboardPage.headerText(),
                    "Dashboard",
                    "User should land on Dashboard after login"
            );

        } catch (AssertionError | RuntimeException e) {
            // Error handling: capture screenshot on failure (best-effort)
            try {
                ElementActions actions = new ElementActions(driver, wait);
                byte[] png = actions.screenshotBytes();
                if (png.length > 0) {
                    Path out = Path.of("target", "screenshots", "TC195-login-failure.png");
                    Files.createDirectories(out.getParent());
                    Files.write(out, png);
                }
            } catch (Exception ignored) {
                // keep original failure intact
            }
            throw e;
        }
    }
}
