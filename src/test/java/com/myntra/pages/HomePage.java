package com.myntra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class HomePage extends BasePage {

    // Updated locators
    private final By searchBox = By.xpath("//input[@class='desktop-searchBar']");
    private final By myntraLogo = By.xpath("//a[contains(@href,'myntra.com')]");
    private final By searchIcon = By.xpath("//span[@class='myntraweb-sprite desktop-iconSearch sprites-headerSearch']");

    public void navigateToHomePage(String url) {
        driver.get(url);
        waitForPageLoad();
        logger.info("✓ Navigated to Myntra homepage: {}", url);

        // Wait a bit for page to fully load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void searchProduct(String productName) {
        // Wait for search box to be visible
        waitForElementVisible(searchBox);
        enterText(searchBox, productName);
        driver.findElement(searchBox).sendKeys(Keys.ENTER);
        logger.info("✓ Searched for product: {}", productName);

        // Wait for search results to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isHomePageLoaded() {
        try {
            // Check if search box is visible (more reliable than logo)
            waitForElementVisible(searchBox);
            logger.info("✓ Home page loaded successfully - Search box is visible");
            return true;
        } catch (Exception e) {
            logger.error("✗ Home page not loaded - Search box not found: {}", e.getMessage());
            return false;
        }
    }

    public String getSearchBoxPlaceholder() {
        return waitForElementVisible(searchBox).getAttribute("placeholder");
    }
}