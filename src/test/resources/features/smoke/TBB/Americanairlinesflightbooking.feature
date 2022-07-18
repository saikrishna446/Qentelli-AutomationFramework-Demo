@AMS @en_US
Feature: American Airlines flight booking mandatory messages

  @en_US
  Scenario: American Airlines flight booking mandatory messages
    Given Navigate to American Airlines site
    And click on Search button
    Then verify the mandatory messages "Please provide us with a departure city and try again." and "Please provide us with a return city and try again."
