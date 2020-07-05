@RunThis @E2E
Feature: Validating User Service APIs

  Scenario: I want to add an user
    Given he/she adds user payload to request
      | firstName | John           |
      | lastName  | Doe            |
      | username  | john           |
      | email     | john@gmail.com |
      | password  | password       |
    When he/she calls "UserAdd" with "POST" request
    Then he/she gets good response "OK"
    And he/she extracts properties from response
      | id       |
      | email    |
      | password |
    And he/she asserts that response has following values
      | Property  | Value |
      | firstName | John  |
      | lastName  | Doe   |
      | username  | john  |

  Scenario: I want to retrieve an user by id
    Given he/she adds user id to request
    When he/she calls "UserFindById" with "GET" request
    Then he/she gets good response "OK"
    And he/she asserts that response has following values
      | Property  | Value |
      | firstName | John  |
      | lastName  | Doe   |
      | username  | john  |

  Scenario: I want to login using email and password
    Given he/she gets authorization using email and password
    When he/she calls "Login" with "GET" request
    Then he/she gets good response "OK"
    And he/she extracts "jwt" from response
    And he/she asserts that properties not null
      | Property |
      | jwt      |

  Scenario: I want to update an user
    Given he/she adds user payload with id to request
      | firstName | John M.        |
      | lastName  | Denver         |
      | username  | john           |
      | email     | john@gmail.com |
      | password  | password       |
    When he/she calls "UserUpdate" with "PUT" request
    Then he/she gets good response "OK"
    And he/she asserts that response has following values
      | Property  | Value   |
      | firstName | John M. |
      | lastName  | Denver  |

  Scenario: I want to delete an user using user id
    Given he/she deletes an user using id
    When he/she calls "UserDelete" with "DELETE" request
    Then he/she gets good response "OK"
    And he/she asserts that response has following values
      | Property | Value                      |
      | message  | User successfully deleted! |
