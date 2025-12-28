package com.myntra.pages;

import com.myntra.factory.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Set;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.actions = new Actions(driver);
    }

    protected WebElement waitForElementVisible(By locator) {
        logger.debug("Waiting for element to be visible: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForElementClickable(By locator) {
        logger.debug("Waiting for element to be clickable: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void clickElement(By locator) {
        waitForElementClickable(locator).click();
        logger.info("Clicked on element: {}", locator);
    }

    protected void enterText(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
        logger.info("Entered text '{}' in element: {}", text, locator);
    }

    protected String getElementText(By locator) {
        String text = waitForElementVisible(locator).getText();
        logger.debug("Retrieved text '{}' from element: {}", text, locator);
        return text;
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void switchToNewWindow() {
        String parentWindow = driver.getWindowHandle();
        wait.until(driver -> driver.getWindowHandles().size() > 1);

        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                logger.info("Switched to new window. Total windows: {}", driver.getWindowHandles().size());
                break;
            }
        }
    }

    protected void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        logger.debug("Scrolled to element");
    }

    protected void waitForPageLoad() {
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
    }

    protected byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    protected String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Current page title: {}", title);
        return title;
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}