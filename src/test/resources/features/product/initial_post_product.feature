Feature: Initial Search for the product

### Please use endpoint GET https://waarkoop-server.herokuapp.com/api/v1/search/demo/{product} for getting the products.
### Available products: "orange", "apple", "pasta", "cola"
### Prepare Positive and negative scenarios

  Scenario: Initial Search for product orange
    When an authorized user call endpoint "https://waarkoop-server.herokuapp.com/api/v1/search/demo/orange"
    Then search result is successful

  Scenario: Initial Search for product apple
    When an authorized user call endpoint "https://waarkoop-server.herokuapp.com/api/v1/search/demo/apple"
    Then all product in search results contains "apple" in "title"

  Scenario: Initial Search for product car
    When an authorized user call endpoint "https://waarkoop-server.herokuapp.com/api/v1/search/demo/car"
    Then does not see the results
