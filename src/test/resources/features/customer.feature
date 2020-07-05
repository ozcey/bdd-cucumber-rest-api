
Feature: Validating Customer APIs

  Scenario: I want to login using email and password
    Given he/she gets authorization
    When he/she calls "Login" with "GET" request
    Then he/she gets good response "OK"
    And he/she extracts "jwt" from response
    And he/she asserts that properties not null
      | Property |
      | jwt      |

  Scenario: I want to add a customer
    Given he/she adds customer payload to request
      | firstName | Sarah              |
      | lastName  | Walker             |
      | username  | sarah              |
      | email     | sarah@gmail.com    |
      | password  | password           |
      | phone     | 111-120-1494       |
      | street    | 111 Browning Point |
      | city      | Columbia           |
      | state     | Maryland           |
      | zipcode   |              21045 |
      | country   | USA                |
    When he/she calls "CustomerAdd" with "POST" request
    Then he/she gets good response "OK"
    And he/she extracts properties from response
    And he/she asserts that response has following values
      | Property  | Value  |
      | firstName | Sarah  |
      | lastName  | Walker |
      | username  | sarah  |

  Scenario: I want to retrieve a customer by id
    Given he/she adds customer id to request
    When he/she calls "CustomerFindById" with "GET" request
    Then he/she gets good response "OK"
    And he/she asserts that response has following values
      | Property  | Value  |
      | firstName | Sarah  |
      | lastName  | Walker |
      | username  | sarah  |

  Scenario: I want to update a customer
    Given he/she adds customer payload with id to request
      | firstName | Sarah M.        |
      | lastName  | Johnson         |
      | username  | sarah           |
      | email     | sarah@gmail.com |
      | password  | password        |
      | phone     | 111-555-1234    |
      | street    | 111 Browning Dr |
      | city      | Columbia        |
      | state     | Maryland        |
      | zipcode   |           21045 |
      | country   | USA             |
    When he/she calls "CustomerUpdate" with "PUT" request
    Then he/she gets good response "OK"
    And he/she asserts that response has following values
      | Property  | Value           |
      | firstName | Sarah M.        |
      | lastName  | Johnson         |
      | phone     | 111-555-1234    |
      | street    | 111 Browning Dr |

  Scenario: I want to delete a customer using customer id
    Given he/she deletes a customer using id
    When he/she calls "CustomerDelete" with "DELETE" request
    Then he/she gets good response "OK"
    And he/she asserts that response has following values
      | Property | Value                      |
      | message  | User successfully deleted! |
