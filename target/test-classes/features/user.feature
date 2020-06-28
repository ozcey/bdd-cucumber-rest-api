Feature: Validating User APIs

  @UserTest
  Scenario: I want to add an user using user payload
    Given he/she adds user payload to request
      | firstName | John           |
      | lastName  | Doe            |
      | username  | john           |
      | email     | john@gmail.com |
      | password  | password       |
    When he/she calls "UserAdd" with "POST" request
    Then he/she gets good response "OK"
    And he/she asserts that response has following values
      | Property  | Value |
      | firstName | John  |
      | lastName  | Doe   |
      | username  | john  |

  @UserTest
  Scenario: I want to retrieve an user by id
    Given he/she adds user id to request
    When he/she calls "UserFindById" with "GET" request
    Then he/she gets good response "OK"
    And he/she asserts that response has following values
      | Property  | Value |
      | firstName | John  |
      | lastName  | Doe   |
      | username  | john  |