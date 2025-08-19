Feature: SauceDemo Login Functionality
  As a user of SauceDemo
  I want to be able to login to the application
  So that I can access the product inventory

  Background:
    Given I am on the SauceDemo login page

  @positive @smoke
  Scenario: Successful login with valid credentials
    When I enter username "standard_user"
    And I enter password "secret_sauce"
    And I click the login button
    Then I should be redirected to the home page
    And I should see the products inventory
    And the page title should contain "Products"

  @negative
  Scenario: Login with invalid username
    When I enter username "invalid_user"
    And I enter password "secret_sauce"
    And I click the login button
    Then I should see an error message
    And I should remain on the login page
    And the error message should contain "Username and password do not match"

  @negative
  Scenario: Login with invalid password
    When I enter username "standard_user"
    And I enter password "invalid_password"
    And I click the login button
    Then I should see an error message
    And I should remain on the login page
    And the error message should contain "Username and password do not match"

  @negative
  Scenario: Login with empty username
    When I enter username ""
    And I enter password "secret_sauce"
    And I click the login button
    Then I should see an error message
    And I should remain on the login page
    And the error message should contain "Username is required"

  @negative
  Scenario: Login with empty password
    When I enter username "standard_user"
    And I enter password ""
    And I click the login button
    Then I should see an error message
    And I should remain on the login page
    And the error message should contain "Password is required"

  @negative
  Scenario: Login with both fields empty
    When I enter username ""
    And I enter password ""
    And I click the login button
    Then I should see an error message
    And I should remain on the login page
    And the error message should contain "Username is required"

  @boundary
  Scenario: Login with extremely long username
    When I enter username "this_is_a_very_long_username_that_exceeds_normal_limits_and_should_be_tested_for_boundary_conditions_in_our_application_testing_framework"
    And I enter password "secret_sauce"
    And I click the login button
    Then I should see an error message
    And I should remain on the login page

  @boundary
  Scenario: Login with extremely long password
    When I enter username "standard_user"
    And I enter password "this_is_a_very_long_password_that_exceeds_normal_limits_and_should_be_tested_for_boundary_conditions_in_our_application_testing_framework_for_security_purposes"
    And I click the login button
    Then I should see an error message
    And I should remain on the login page

#  @positive
#  Scenario: Successful logout after login
#    When I enter username "standard_user"
#    And I enter password "secret_sauce"
#    And I click the login button
#    And I should be redirected to the home page
#    When I click on the menu button
#    And I click on logout
#    Then I should be redirected back to the login page
#    And I should see the login form

  @positive
  Scenario: Verify home page elements after successful login
    When I enter username "standard_user"
    And I enter password "secret_sauce"
    And I click the login button
    Then I should be redirected to the home page
    And I should see the app logo
    And I should see the shopping cart icon
    And I should see at least 6 products
    And I should see product names and prices