package com.myntra.stepdefinitions;

import com.myntra.factory.DriverFactory;
import com.myntra.pages.*;
import com.myntra.utils.ConfigReader;
import io.cucumber.java.en.*;
import org.testng.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyntraStepDefinitions {

    private static final Logger logger = LoggerFactory.getLogger(MyntraStepDefinitions.class);

    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private ProductDetailsPage productDetailsPage;
    private ShoppingBagPage shoppingBagPage;
    private String baseUrl;

    public MyntraStepDefinitions() {
        this.homePage = new HomePage();
        this.searchResultsPage = new SearchResultsPage();
        this.productDetailsPage = new ProductDetailsPage();
        this.shoppingBagPage = new ShoppingBagPage();
        this.baseUrl = ConfigReader.getProperty("base.url");
    }

    @Given("User navigates to Myntra homepage")
    public void user_navigates_to_myntra_homepage() {
        homePage.navigateToHomePage(baseUrl);
        Assert.assertTrue(homePage.isHomePageLoaded(), "Home page is not loaded");
        logger.info("✓ Step: User navigated to Myntra homepage");
    }

    @When("User searches for {string}")
    public void user_searches_for(String productName) {
        homePage.searchProduct(productName);
        logger.info("✓ Step: User searched for product: {}", productName);
    }

    @When("User clicks on the first product from search results")
    public void user_clicks_on_the_first_product_from_search_results() {
        Assert.assertTrue(searchResultsPage.areSearchResultsDisplayed(),
                "Search results are not displayed");
        searchResultsPage.clickFirstProduct();
        logger.info("✓ Step: User clicked on first product");
    }

    @When("User selects a product size")
    public void user_selects_a_product_size() {
        productDetailsPage.selectSize();
        logger.info("✓ Step: User selected product size");
    }

    @When("User adds the product to bag")
    public void user_adds_the_product_to_bag() {
        Assert.assertTrue(productDetailsPage.isAddToBagButtonDisplayed(),
                "Add to Bag button is not displayed");
        productDetailsPage.addProductToBag();
        logger.info("✓ Step: User added product to bag");
    }

    @Then("Product should be added to bag successfully")
    public void product_should_be_added_to_bag_successfully() {
        Assert.assertTrue(shoppingBagPage.isGoToBagButtonDisplayed(),
                "Go to Bag button is not displayed - Product not added");
        logger.info("✓ Step: Product added to bag successfully verified");
    }

    @When("User navigates to shopping bag")
    public void user_navigates_to_shopping_bag() {
        shoppingBagPage.navigateToShoppingBag();

        // Now we're in the cart page (same window as product details)
        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        logger.info("Current URL after navigating to bag: {}", currentUrl);

        // Verify products are in bag
        Assert.assertTrue(shoppingBagPage.isProductInBag(),
                "No products found in bag. Current URL: " + currentUrl);

        logger.info("✓ Step: User navigated to shopping bag with products");
    }

    @When("User clicks on place order button")
    public void user_clicks_on_place_order_button() {
        Assert.assertTrue(shoppingBagPage.isPlaceOrderButtonDisplayed(),
                "Place Order button is not displayed");
        shoppingBagPage.clickPlaceOrder();
        logger.info("✓ Step: User clicked on Place Order button");
    }

    @Then("User should be navigated to checkout page")
    public void user_should_be_navigated_to_checkout_page() {
        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("checkout") || currentUrl.contains("login"),
                "User is not navigated to checkout/login page. Current URL: " + currentUrl);
        logger.info("✓ Step: User navigated to checkout page successfully");
        logger.info("✓ E2E Test Completed Successfully! Thank You!");
    }
}