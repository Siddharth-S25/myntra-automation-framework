@E2ETest @Regression
Feature: Myntra E-commerce End-to-End User Journey
  As a user of Myntra
  I want to search for products, add them to cart, and proceed to checkout
  So that I can complete my purchase successfully

  Background:
    Given User navigates to Myntra homepage

  @Smoke @PositiveTest
  Scenario: Complete user journey - Search product, add to cart and verify checkout
    When User searches for "best formal shoes for men under 1500"
    And User clicks on the first product from search results
    And User selects a product size
    And User adds the product to bag
    Then Product should be added to bag successfully
    When User navigates to shopping bag
    And User clicks on place order button
    Then User should be navigated to checkout page

  @Smoke
  Scenario Outline: Search and add multiple products to cart
    When User searches for "<product>"
    And User clicks on the first product from search results
    And User selects a product size
    And User adds the product to bag
    Then Product should be added to bag successfully

    Examples:
      | product                                  |
      | best formal shoes for men under 1500    |
      | casual shirts for men                    |