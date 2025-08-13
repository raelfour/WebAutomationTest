Feature: Login Functionality
  As a website user
  I want to be able to login to the system
  So that I can access the available features

  Background:
    Given I am on the login page

  @positive
  Scenario: Successful login with valid credentials
    When I enter username "tomsmith"
    And I enter password "SuperSecretPassword!"
    And I click the login button
    Then I should be successfully logged in and redirected to secure page
    And I should see the message "You logged into a secure area!"

  @negative
  Scenario: Failed login with invalid username
    When I enter username "wronguser"
    And I enter password "SuperSecretPassword!"
    And I click the login button
    Then I should not be logged in
    And I should see error message "Your username is invalid!"

  @negative
  Scenario: Failed login with invalid password
    When I enter username "tomsmith"
    And I enter password "wrongpassword"
    And I click the login button
    Then I should not be logged in
    And I should see error message "Your password is invalid!"

  @boundary
  Scenario: Login with empty username
    When I enter username ""
    And I enter password "SuperSecretPassword!"
    And I click the login button
    Then I should not be logged in
    And I should see error message "Your username is invalid!"

  @boundary
  Scenario: Login with empty password
    When I enter username "tomsmith"
    And I enter password ""
    And I click the login button
    Then I should not be logged in
    And I should see error message "Your password is invalid!"

  @boundary
  Scenario Outline: Login with extremely long username
    When I enter username "<username>"
    And I enter password "SuperSecretPassword!"
    And I click the login button
    Then I should not be logged in
    And I should see error message "Your username is invalid!"

    Examples:
      | username |
      | abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz |
      | 1234567890123456789012345678901234567890123456789012345678901234567890 |