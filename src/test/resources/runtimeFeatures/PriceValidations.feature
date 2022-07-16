@API @en_US
Feature: Validating Place API's

  @en_US
  Scenario: Verify if Place is being Succesfully added using AddPlaceAPI
    Given Add Place Payload with "AAhouse"  "English" "World cross center"
    When user calls "AddPlaceAPI" with "POST" http request
    Then the API call got success with status code 200
    And verify place_Id created maps to "AAhouse" using "getPlaceAPI"
