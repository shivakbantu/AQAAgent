package org.example.tests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import org.example.core.BrowserSession;
import org.example.core.Config;
import org.example.core.ConfigLoader;
import org.example.core.ElementActions;
import org.example.data.builders.LoginCredentials;
import org.example.data.builders.LoginCredentialsBuilder;
import org.example.pages.DashboardPage;
import org.example.pages.OrangeHrmLoginPage;
import org.example.pages.factory.PageObjectFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * TestRail: TC_003 (Case ID: 235)
 * Title: Verify successful login with valid credentials (Admin/admin123)
 */
public class TC003VerifySuccessfulLoginWithValidCredentialsTest {

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
    public void verifySuccessfulLoginWithValidCredentials() throws Exception {
        // Arrange (Builder)
        LoginCredentials credentials = LoginCredentialsBuilder.fromEnvOrSystemProps();

        // Arrange (Factory)
        PageObjectFactory pages = new PageObjectFactory(driver, wait);
        OrangeHrmLoginPage loginPage = pages.loginPage();
        DashboardPage dashboardPage = pages.dashboardPage();

        try {
            // Precondition: User is on the OrangeHRM login page.
            ensureAtLogin(config.baseUrl(), loginPage);

            // Step 1: Enter `Admin` in Username.
            loginPage.enterUsername(credentials.username());

            // Step 2: Enter `admin123` in Password.
            loginPage.enterPassword(credentials.password());

            // Step 3: Click Login.
            loginPage.clickLogin();

            // Expected: User is redirected to the OrangeHRM Dashboard and the login page is no longer displayed.
            Assert.assertTrue(
                    dashboardPage.isAtDashboard(),
                    "Expected to be on Dashboard URL, but was: " + dashboardPage.currentUrl()
            );
            Assert.assertEquals(
                    dashboardPage.getModuleHeaderText(),
                    "Dashboard",
                    "Expected Dashboard header to be 'Dashboard'."
            );
            Assert.assertFalse(
                    loginPage.isCurrentUrlLogin(),
                    "Expected login page URL to no longer be displayed after successful login."
            );
            Assert.assertFalse(
                    loginPage.isLoginFormPresent(),
                    "Expected login form to no longer be displayed after successful login."
            );

        } catch (AssertionError | RuntimeException e) {
            // Best-effort screenshot on failure
            try {
                ElementActions actions = new ElementActions(driver, wait);
                byte[] png = actions.screenshotBytes();
                if (png.length > 0) {
                    Path out = Path.of("target", "screenshots", "TC003-login-failure.png");
                    Files.createDirectories(out.getParent());
                    Files.write(out, png);
                }
            } catch (Exception ignored) {
                // keep failure reason intact
            }
            throw e;
        }
    }

    private void ensureAtLogin(String loginUrl, OrangeHrmLoginPage loginPage) {
        // If an existing session redirects to Dashboard, force logout and re-open Login to satisfy precondition.
        driver.get(loginUrl);
        if (!loginPage.isLoginFormPresent() || driver.getCurrentUrl().contains("/web/index.php/dashboard")) {
            driver.get(toLogoutUrl(loginUrl));
        }
        loginPage.open(loginUrl);
        Assert.assertTrue(loginPage.isLoginFormPresent(), "Precondition failed: Login form is not displayed.");
    }

    private String toLogoutUrl(String loginUrl) {
        // Example loginUrl: https://.../web/index.php/auth/login
        String base = loginUrl.split("/web/index.php/")[0];
        return base + "/web/index.php/auth/logout";
    }
}