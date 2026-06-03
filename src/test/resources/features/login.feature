Feature: Login

  @smoke @login
  Scenario Outline: Successful login with valid credentials
    Given user navigates to login page
    When user enters username "<username>" and password "<password>"
    Then user sees dashboard

    Examples:
      | username | password |
      | Admin    | admin123 |

  @regression @login
  Scenario Outline: Login fails with invalid credentials
    Given user navigates to login page
    When user enters username "<username>" and password "<password>"
    Then user sees invalid credentials message

    Examples:
      | username | password |
      | Admin    | wrongpass |

  @regression @login @data
  Scenario: Successful login using external test data file
    Given user navigates to login page
    When user logs in with valid credentials from "testdata/login.json"
    Then user sees dashboard

