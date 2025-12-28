package com.myntra.pages;

import org.openqa.selenium.By;

public class ShoppingBagPage extends BasePage {

    // Correct working locator - Found through debugging!
    private final By cartItems = By.xpath("//div[contains(@class,'itemContainer')]");

    // Buttons
    private final By goToBagButton = By.xpath("//a[.//span[text()='GO TO BAG']]");
    private final By placeOrderButton = By.xpath("//button[@role='button' and (normalize-space()='PLACE ORDER')]");
    private final By removeButton = By.xpath("//div[@class='inlineButtonV2-base-actionButton itemComponents-base-remove']");
    private final By totalAmount = By.xpath("//span[@class='price-value bold-font']");

    /**
     * Navigates to shopping bag - Same window, just waits for page to load
     */
    public void navigateToShoppingBag() {
        try {
            logger.info("Waiting for GO TO BAG button...");
            Thread.sleep(3000);

            // Use your exact working locator
            waitForElementClickable(goToBagButton).click();
            logger.info("✓ Successfully clicked on GO TO BAG button");

            // Wait for cart page to load in same window
            waitForPageLoad();
            Thread.sleep(3000); // Wait for items to render

            logger.info("✓ Successfully navigated to shopping bag");
            logger.info("Current URL: {}", driver.getCurrentUrl());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Interrupted while navigating to bag");
        }
    }

    /**
     * Clicks on Place Order button
     */
    public void clickPlaceOrder() {
        try {
            logger.info("Looking for PLACE ORDER button...");

            // Use your exact working locator
            waitForElementClickable(placeOrderButton).click();
            logger.info("✓ Successfully clicked on PLACE ORDER button");

        } catch (Exception e) {
            logger.error("Failed to click Place Order button: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Verifies if products are in the bag
     */
    public boolean isProductInBag() {
        try {
            logger.info("Checking if products are in bag...");
            Thread.sleep(2000);

            boolean hasItems = isElementDisplayed(cartItems);
            int itemCount = driver.findElements(cartItems).size();

            if (hasItems) {
                logger.info("✓ Products found in bag: {} item(s)", itemCount);
            } else {
                logger.error("✗ No products found in bag");
                logger.error("Current URL: {}", driver.getCurrentUrl());
            }

            return hasItems;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * Gets count of items in bag
     */
    public int getItemCount() {
        int count = driver.findElements(cartItems).size();
        logger.info("Total items in bag: {}", count);
        return count;
    }

    /**
     * Gets total amount
     */
    public String getTotalAmount() {
        if (isElementDisplayed(totalAmount)) {
            String amount = getElementText(totalAmount);
            logger.info("Total amount: {}", amount);
            return amount;
        }
        return "";
    }

    /**
     * Verifies if Place Order button is displayed
     */
    public boolean isPlaceOrderButtonDisplayed() {
        boolean displayed = isElementDisplayed(placeOrderButton);
        if (displayed) {
            logger.info("✓ PLACE ORDER button is displayed");
        }
        return displayed;
    }

    /**
     * Verifies if Go to Bag button is displayed
     */
    public boolean isGoToBagButtonDisplayed() {
        try {
            Thread.sleep(2000);
            boolean displayed = isElementDisplayed(goToBagButton);

            if (displayed) {
                logger.info("✓ GO TO BAG button is displayed");
            } else {
                logger.error("✗ GO TO BAG button is NOT displayed");
            }

            return displayed;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}