package org.example.core;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementActions {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public ElementActions(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void type(By locator, String value) {
        WebElement el = visible(locator);
        el.clear();
        el.sendKeys(value);
    }

    public void click(By locator) {
        clickable(locator).click();
    }

    public String text(By locator) {
        return visible(locator).getText();
    }

    public byte[] screenshotBytes() {
        if (driver instanceof TakesScreenshot ts) {
            return ts.getScreenshotAs(OutputType.BYTES);
        }
        return new byte[0];
    }

    public static WebDriverWait wait(WebDriver driver, long seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }
}

