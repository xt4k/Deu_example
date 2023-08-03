Feature: Search for the product

### Please use endpoint GET https://waarkoop-server.herokuapp.com/api/v1/search/demo/{product} for getting the products.
### Available products: "orange", "apple", "pasta", "cola"
### Prepare positive and negative scenarios
  @positive
  Scenario Outline: Search test with available product
    When I am searching for <product>
    Then search result is successful
    And search result should contain <product>
    And the object structure should match to specification defined in "product.json"
    Examples:
      | product |
      | apple   |
      | cola    |
      | orange  |
      | pasta   |

  @negative
  Scenario Outline: Search unavailable products
    When I am searching for <product>
    Then search result return not found error
    Examples:
      | product |
      | car     |
      | coffee  |

  @negative
  Scenario: Search test without product
    When I am searching without "product"
    Then verify not authenticated error should be displayed in search result

  @positive
  Scenario: Search for product apple
    When I do search for product "apple"
    Then search result is successful
    Then all product in search results contains "apple" in "title"

  @positive
  Scenario: Search for product cola
    When I do search for product "cola"
    Then search result is successful
