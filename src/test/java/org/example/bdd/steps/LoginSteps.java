package org.example.bdd.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import java.util.Map;
import org.example.core.Config;
import org.example.core.ConfigLoader;
import org.example.core.DriverManager;
import org.example.core.ElementActions;
import org.example.data.TestDataLoader;
import org.example.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LoginSteps {
    private final Config config = ConfigLoader.load();

    private LoginPage loginPage;
    private WebDriverWait wait;

    @Given("user navigates to login page")
    public void userNavigatesToLoginPage() {
        WebDriver driver = DriverManager.getDriver();
        wait = ElementActions.wait(driver, config.explicitWaitSeconds());
        loginPage = new LoginPage(driver, wait);
        loginPage.open(config.baseUrl());
    }

    @When("user logs in with valid credentials from {string}")
    public void userLogsInWithValidCredentialsFrom(String classpathDataFile) {
        List<Map<String, String>> rows = TestDataLoader.load(classpathDataFile);
        Map<String, String> first = rows.getFirst();
        loginPage.login(first.get("username"), first.get("password"));
    }

    @When("user enters username {string} and password {string}")
    public void userEntersUsernameAndPassword(String username, String password) {
        loginPage.login(username, password);
    }

    @Then("user sees dashboard")
    public void userSeesDashboard() {
        Assert.assertEquals(loginPage.getDashboardHeaderText(), "Dashboard", "User should land on Dashboard after login");
    }

    @Then("user sees invalid credentials message")
    public void userSeesInvalidCredentialsMessage() {
        Assert.assertTrue(loginPage.getInvalidCredentialsMessage().toLowerCase().contains("invalid"),
                "Expected invalid credentials message");
    }
}

