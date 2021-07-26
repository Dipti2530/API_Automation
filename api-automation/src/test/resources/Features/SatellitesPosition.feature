Feature: To validate Get Satellites Position endpoint

  Scenario Outline: Validate GET Satellites position endpoint returns details as expected for given timestamps (past and future timestamps)
    When a request is sent to get Satellite position of "25544" on "v1" endpoint with
      | paramName  | paramValue                      |
      | timestamps | 1436029892,143602990,1658869056 |
      | units      | <units>                         |
    Then success response is received with HTTP status code 200
    And response contains satellite position details as expected
      | name | id    | units   |
      | iss  | 25544 | <units> |
    Examples:
      | units      |
      | miles      |
      | kilometers |


  Scenario: Validate that GET Satellites position endpoint returns an error when satellite Id is not found
    When a request is sent to get Satellite position of "1234" on "v1" endpoint with
      | paramName  | paramValue            |
      | timestamps | 1436029892,1436029902 |
      | units      | miles                 |
    Then an error should be returned as "satellite not found" with HTTP status code 404


  Scenario: Validate that GET Satellites position endpoint returns an error when timestamps is missing in the request
    When a request is sent to get Satellite position of "25544" on "v1" endpoint with
      | paramName | paramValue |
      | units     | miles      |
    Then an error should be returned as "invalid timestamp in list: " with HTTP status code 400


  Scenario: Validate that GET Satellites position endpoint returns an error when timestamp format is invalid in the request
    When a request is sent to get Satellite position of "25544" on "v1" endpoint with
      | paramName  | paramValue |
      | timestamps | 3ac5433    |
      | units      | miles      |
    Then an error should be returned as "invalid timestamp in list: 3ac5433" with HTTP status code 400


  Scenario: Validate that default unit is kilometers for GET Satellites position endpoint when unit is not present in the request
    When a request is sent to get Satellite position of "25544" on "v1" endpoint with
      | paramName  | paramValue            |
      | timestamps | 1436029892,1436029902 |
    Then success response is received with HTTP status code 200
    And response contains satellite position details as expected
      | name | id    | units      |
      | iss  | 25544 | kilometers |


  Scenario Outline: Validate that default unit is kilometers for GET Satellites position endpoint when invalid/ incorrect unit is passed in the request
    When a request is sent to get Satellite position of "25544" on "v1" endpoint with
      | paramName  | paramValue            |
      | timestamps | 1436029892,1436029902 |
      | units      | <units>               |
    Then success response is received with HTTP status code 200
    And response contains satellite position details as expected
      | name | id    | units      |
      | iss  | 25544 | kilometers |

    Examples:
      | units    |
      | miles12  |
      | 124yyftf |


  Scenario Outline: Validate that error is returned when GET Satellite position request is sent to incorrect endpoint version
    When a request is sent to get Satellite position of "25544" on "v1" endpoint with
      | paramName  | paramValue            |
      | timestamps | 1436029892,1436029902 |
      | units      | <units>               |
    Then success response is received with HTTP status code 200
    And response contains satellite position details as expected
      | name | id    | units      |
      | iss  | 25544 | kilometers |

    Examples:
      | units    |
      | miles12  |
      | 124yyftf |


  Scenario: Validate that an error is returned when GET Satellite position request is sent to incorrect endpoint version
    When a request is sent to get Satellite position of "25544" on "v2" endpoint with
      | paramName  | paramValue            |
      | timestamps | 1436029892,1436029902 |
      | units      | <units>               |
    Then an error should be returned as "Invalid API version" with HTTP status code 400


