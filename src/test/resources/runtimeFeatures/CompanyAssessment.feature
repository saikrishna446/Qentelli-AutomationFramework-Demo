@Regression @en_US
Feature: Verify the Company Assessment

  @en_US
  Scenario:Verify the Company Assessment
    Given User navigates to Home Page and starts the Assessment
    Then Enter the Demographic details
    And Completes the Assessment
    Then Validates the results