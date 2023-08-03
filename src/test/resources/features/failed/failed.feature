Feature: Failed tests

  @negative
  Scenario: Unauthorized user call
    When an unauthorized user call "https://api.heroku.com/account"
    Then search result return not found error

  @positive
  Scenario: Authorized user call
    When an authorized user call endpoint "https://api.heroku.com/account"
    Then search result return not found error

  @Issue
    ### Here is product bug example (when "according to requirement" we expect that product title should contain product name
    ### but in real life - some product doesn't. In that case we need to create bug with following - requirement and tests updates
    ### or just exclude this test from testsuite (as temporary solution, after discussion it with the team).
  Scenario: Product title contain product name
    When I am searching for "cola"
    Then search result is successful
    Then all product in search results contains "cola" in "title"
