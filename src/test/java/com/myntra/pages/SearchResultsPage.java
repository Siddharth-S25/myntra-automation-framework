package com.myntra.pages;

import org.openqa.selenium.By;

public class SearchResultsPage extends BasePage {

    private final By firstProduct = By.xpath("//li[@class='product-base'][1]");
    private final By productList = By.xpath("//li[@class='product-base']");
    private final By resultsCount = By.xpath("//span[@class='title-count']");

    /**
     * Clicks on the first product from search results
     * Product opens in NEW WINDOW - switches to it
     */
    public void clickFirstProduct() {
        logger.info("Clicking on first product...");

        // Store parent window handle BEFORE clicking
        String parentWindow = driver.getWindowHandle();
        logger.info("Parent window: {}", parentWindow);

        // Click on first product
        waitForElementClickable(firstProduct).click();
        logger.info("✓ Clicked on first product");

        // Wait for new window to open
        wait.until(driver -> driver.getWindowHandles().size() > 1);
        logger.info("New window opened. Total windows: {}", driver.getWindowHandles().size());

        // Switch to the new window
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(parentWindow)) {
                driver.switchTo().window(windowHandle);
                logger.info("✓ Switched to product details window");
                break;
            }
        }

        // Wait for page to load
        waitForPageLoad();
        logger.info("Product page loaded: {}", driver.getTitle());
    }

    /**
     * Gets the count of search results
     */
    public int getProductCount() {
        return driver.findElements(productList).size();
    }

    /**
     * Verifies if search results are displayed
     */
    public boolean areSearchResultsDisplayed() {
        return isElementDisplayed(productList);
    }

    /**
     * Gets results count text
     */
    public String getResultsCountText() {
        if (isElementDisplayed(resultsCount)) {
            return getElementText(resultsCount);
        }
        return "Results count not displayed";
    }
}