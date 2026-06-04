package org.example.pages;

import org.example.core.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * POM for OrangeHRM Login page (selectors verified via Playwright MCP).
// Used by: TC_002 (TestRail Case ID: 149)

 */
public class OrangeHrmLoginPage {

    // Playwright-confirmed selectors
    private static final By USERNAME_INPUT = By.cssSelector("input[name='username']");
    private static final By PASSWORD_INPUT = By.cssSelector("input[name='password']");
    private static final By LOGIN_BUTTON   = By.cssSelector("button[type='submit']");

    private final WebDriver driver;
    private final ElementActions actions;

    public OrangeHrmLoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.actions = new ElementActions(driver, wait);
    }

    public void open(String url) {
        driver.get(url);
        // Guard: login form should be visible
        actions.visible(USERNAME_INPUT);
        actions.visible(PASSWORD_INPUT);
        actions.visible(LOGIN_BUTTON);
    }

    public void enterUsername(String username) {
        actions.type(USERNAME_INPUT, username);
    }

    public void enterPassword(String password) {
        actions.type(PASSWORD_INPUT, password);
    }

    public void clickLogin() {
        actions.click(LOGIN_BUTTON);
    }

    /**
     * Used to assert login page is no longer displayed after successful login.
     */
    public boolean isLoginFormPresent() {
        return !driver.findElements(USERNAME_INPUT).isEmpty()
                && !driver.findElements(PASSWORD_INPUT).isEmpty()
                && !driver.findElements(LOGIN_BUTTON).isEmpty();
    }
}
