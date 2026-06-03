package org.example.pages;

import org.example.core.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriver driver;
    private final ElementActions actions;

    // Locator preference order: data-testid, id, name, css, xpath
    private final By usernameInput = By.cssSelector("input[name='username']");
    private final By passwordInput = By.cssSelector("input[name='password']");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By dashboardHeader = By.cssSelector("h6.oxd-text--h6");
    private final By invalidCredentialsToast = By.cssSelector("p.oxd-alert-content-text");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.actions = new ElementActions(driver, wait);
    }

    public void open(String url) {
        driver.get(url);
    }

    public void login(String username, String password) {
        actions.type(usernameInput, username);
        actions.type(passwordInput, password);
        actions.click(loginButton);
    }

    public String getDashboardHeaderText() {
        return actions.text(dashboardHeader);
    }

    public String getInvalidCredentialsMessage() {
        return actions.text(invalidCredentialsToast);
    }
}
