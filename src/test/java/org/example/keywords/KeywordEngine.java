package org.example.keywords;

import java.util.List;
import org.example.core.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class KeywordEngine {
    private final WebDriver driver;
    private final ElementActions actions;
    private final ObjectRepository or;

    public KeywordEngine(WebDriver driver, ElementActions actions, ObjectRepository objectRepository) {
        this.driver = driver;
        this.actions = actions;
        this.or = objectRepository;
    }

    public void execute(List<StepDefinitionRow> steps) {
        for (StepDefinitionRow step : steps) {
            execute(step);
        }
    }

    public String execute(StepDefinitionRow step) {
        return switch (step.keyword()) {
            case NAVIGATE -> {
                driver.get(step.value());
                yield "";
            }
            case TYPE -> {
                By locator = or.getLocator(step.target());
                actions.type(locator, step.value());
                yield "";
            }
            case CLICK -> {
                By locator = or.getLocator(step.target());
                actions.click(locator);
                yield "";
            }
            case GET_TEXT -> {
                By locator = or.getLocator(step.target());
                yield actions.text(locator);
            }
        };
    }
}

