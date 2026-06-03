package org.example.tests;

import java.util.Map;
import org.example.core.BaseTest;
import org.example.core.Config;
import org.example.core.ConfigLoader;
import org.example.data.providers.TestNgDataProviders;
import org.example.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void validLoginNavigatesToDashboard() {
        Config config = ConfigLoader.load();
        String username = System.getProperty("app.username", "Admin");
        String password = System.getProperty("app.password", "admin123");

        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open(config.baseUrl());
        loginPage.login(username, password);

        Assert.assertEquals(loginPage.getDashboardHeaderText(), "Dashboard", "User should land on Dashboard after login");
    }

    @Test(dataProvider = "loginData", dataProviderClass = TestNgDataProviders.class)
    public void loginDataDriven(Map<String, String> row) {
        Config config = ConfigLoader.load();

        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open(config.baseUrl());
        loginPage.login(row.get("username"), row.get("password"));

        String expected = row.getOrDefault("expected", "").trim();
        if ("Dashboard".equalsIgnoreCase(expected)) {
            Assert.assertEquals(loginPage.getDashboardHeaderText(), "Dashboard");
        } else {
            Assert.assertTrue(loginPage.getInvalidCredentialsMessage().toLowerCase().contains("invalid"));
        }
    }
}
