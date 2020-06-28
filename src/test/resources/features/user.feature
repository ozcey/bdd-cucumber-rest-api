Feature: Validating User APIs

  Scenario: I want to add an user using user payload
    Given he/she adds user payload
      | firstName | John           |
      | lastName  | Doe            |
      | username  | john           |
      | email     | john@gmail.com |
      | password  | password       |
    When he/she calls "UserAdd" with "POST" request
    Then he/she gets good response "OK"
    And he/she asserts that response has following values
      | Property | Value |
      | username | john  |
