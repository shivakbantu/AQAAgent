package org.example.pages;

import org.example.core.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * OrangeHRM Login Page (POM).
 * Selectors are taken from Playwright MCP live inspection.
 */
public class OrangeHrmLoginPageV2 {

    private final WebDriver driver;
    private final ElementActions actions;

    // Playwright-confirmed selectors
    private final By usernameInput = By.cssSelector("input[name='username']");
    private final By passwordInput = By.cssSelector("input[name='password']");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By loginForm = By.cssSelector("form.oxd-form");

    public OrangeHrmLoginPageV2(WebDriver driver, ElementActions actions) {
        this.driver = driver;
        this.actions = actions;
    }

    public void open(String url) {
        driver.get(url);
        // guard
        actions.visible(loginForm);
    }

    public boolean isLoginFormDisplayed() {
        try {
            actions.visible(loginForm);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void enterUsername(String username) {
        actions.type(usernameInput, username);
    }

    public void enterPassword(String password) {
        actions.type(passwordInput, password);
    }

    public void clickLogin() {
        actions.click(loginButton);
    }
}
