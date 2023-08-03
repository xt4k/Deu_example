Feature: Account information

  @negative
  Scenario: Unauthorized user call
    When an unauthorized user call "https://api.heroku.com/account"
    Then get unauthorized error message
    And response field "id" has value "unauthorized"


  @positive
  Scenario: Authorized user call
    When an authorized user call endpoint "https://api.heroku.com/account"
    Then search result is successful
    And the object structure should match to specification defined in "account.json"
    And account contains user name


  @positive
  Scenario: Get authorized user account rate limit
    When an authorized user call endpoint "https://api.heroku.com/account/rate-limits"
    Then search result is successful
    And response field "remaining" value is about "4500"