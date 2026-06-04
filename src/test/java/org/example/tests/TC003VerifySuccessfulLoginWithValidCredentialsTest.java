package org.example.tests;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**

    private Config config;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        config = ConfigLoader.load();

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

