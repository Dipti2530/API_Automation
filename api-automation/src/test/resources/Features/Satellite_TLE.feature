Feature: Validate that TLE data for a given satellite is returned

  Scenario: Validate that GET TLE endpoint returns satellite data with default format as JSON when format is not mentioned in the request
    When a request is sent to get TLE data for satellite "25544" on "v1" endpoint
    Then success response is received with HTTP status code 200
    And expected response with TLE data is returned with
      | id    | name | Content-Type     |
      | 25544 | iss  | application/json |


  Scenario: Validate that GET TLE endpoint returns satellite data with format as text
    When a request is sent to get TLE data for satellite "25544" on "v1" endpoint with
      | paramName | paramValue |
      | format    | text       |
    Then success response is received with HTTP status code 200
    And expected response with TLE data is returned with contentType as "text/plain"


  Scenario Outline: Validate that GET TLE endpoint returns satellite data with default format as JSON when incorrect format is provided in request
    When a request is sent to get TLE data for satellite "25544" on "v1" endpoint with
      | paramName   | paramValue   |
      | <paramName> | <paramValue> |
    Then success response is received with HTTP status code 200
    And expected response with TLE data is returned with
      | id    | name | Content-Type     |
      | 25544 | iss  | application/json |

    Examples:
      | paramName | paramValue |
      | format    | TEXT       |
      | format    | 123text    |
      | format    | abcde15    |


  Scenario: Validate an error when satellite Id is not found
    When a request is sent to get TLE data for satellite "12345" on "v1" endpoint
    Then an error should be returned as "satellite not found" with HTTP status code 404


  Scenario: Validate an error when incorrect endpoint version is called
    When a request is sent to get TLE data for satellite "25544" on "v2" endpoint
    Then an error should be returned as "Invalid API version" with HTTP status code 400
