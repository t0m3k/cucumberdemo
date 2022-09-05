@API
Feature: API Tests

  Scenario: Get all products
    When I request all products
    Then I get a status code of 200
  Scenario: Check a single user
    When I request user number 2
    Then I get a status code of 200
    And the user is "Jane Smith"