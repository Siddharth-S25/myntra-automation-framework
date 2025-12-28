package com.myntra.stepdefinitions;

import com.myntra.factory.DriverFactory;
import com.myntra.utils.ConfigReader;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private DriverFactory driverFactory;

    @Before
    public void setUp(Scenario scenario) {
        logger.info("=".repeat(80));
        logger.info("Starting Scenario: {}", scenario.getName());
        logger.info("=".repeat(80));

        String browser = ConfigReader.getProperty("browser");
        driverFactory = new DriverFactory();
        driverFactory.initDriver(browser);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.error("Scenario Failed: {}", scenario.getName());
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
            logger.info("Screenshot captured for failed scenario");
        }

        logger.info("=".repeat(80));
        logger.info("Scenario Status: {}", scenario.getStatus());
        logger.info("Completing Scenario: {}", scenario.getName());
        logger.info("=".repeat(80));

        DriverFactory.quitDriver();
    }

    @BeforeStep
    public void beforeStep(Scenario scenario) {
        // Can be used for step-level logging or setup
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        // Capture screenshot after each step (optional)
    }
}