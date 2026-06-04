package org.example.tests;

import org.example.core.BaseTest;
import org.example.core.Config;
import org.example.core.ConfigLoader;
import org.example.data.builders.LoginCredentials;
import org.example.data.builders.LoginCredentialsBuilder;
import org.example.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Acceptance Criteria: User can log in successfully with valid credentials.
 *
 * Credentials resolution order:
 * 1) JVM system properties: -Dapp.username / -Dapp.password
 * 2) Environment variables: ORANGEHRM_USERNAME / ORANGEHRM_PASSWORD
 */
public class TC003VerifySuccessfulLoginWithValidCredentialsTest extends BaseTest {

    @Test
    public void verifySuccessfulLoginWithValidCredentials() {
        Config config = ConfigLoader.load();
        LoginCredentials creds = LoginCredentialsBuilder.fromEnvOrSystemProps();

        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open(config.baseUrl());
        loginPage.login(creds.username(), creds.password());

        Assert.assertEquals(
                loginPage.getDashboardHeaderText(),
                "Dashboard",
                "User should land on Dashboard after login with valid credentials"
        );
    }
}
