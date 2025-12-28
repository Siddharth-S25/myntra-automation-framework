package com.myntra.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductDetailsPage extends BasePage {

    // Using your exact working locators
    private final By sizeButtons = By.xpath("//div[@class='size-buttons-buttonContainer']");
    private final By addToBagButton = By.xpath("//div[text()='ADD TO BAG']");

    /**
     * Selects product size (second size button)
     * Matches your working code: sizeButtons.get(1).click()
     */
    public void selectSize() {
        try {
            logger.info("Waiting for size buttons...");

            // Use explicit wait like your code
            List<WebElement> sizes = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(sizeButtons)
            );

            if (!sizes.isEmpty()) {
                // Select second size (index 1) like your code
                sizes.get(1).click();
                logger.info("✓ Selected product size at index: 1");
            } else {
                logger.warn("No size buttons found!");
            }

        } catch (Exception e) {
            logger.error("Error selecting size: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Adds product to bag
     * Matches your working code exactly
     */
    public void addProductToBag() {
        try {
            // Wait for Add to Bag button like your code
            waitForElementClickable(addToBagButton);

            String productName = driver.getTitle();
            logger.info("Selected Product is: {}", productName);

            // Click Add to Bag
            clickElement(addToBagButton);
            logger.info("✓ Product is added to cart");

            // Wait for action to complete
            Thread.sleep(3000);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Interrupted while adding to bag");
        } catch (Exception e) {
            logger.error("Error adding product to bag: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Gets product title
     */
    public String getProductTitle() {
        return driver.getTitle();
    }

    /**
     * Verifies if Add to Bag button is displayed
     */
    public boolean isAddToBagButtonDisplayed() {
        return isElementDisplayed(addToBagButton);
    }
}