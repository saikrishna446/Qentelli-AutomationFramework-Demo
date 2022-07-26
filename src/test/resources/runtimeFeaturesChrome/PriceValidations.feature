@API @en_US
Feature: AddPlaceAPI POST API

  @en_US
  Scenario: Verify AddPlaceAPI request
    Given Add Place Payload with "AAhouse"  "English" "World cross center"
    When user calls "AddPlaceAPI" with "POST" http request
    Then the API call got success with status code 200
    And verify place_Id created maps to "AAhouse" using "getPlaceAPI"
