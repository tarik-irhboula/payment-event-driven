Feature: Account Creation
  I want to create a new account

  Scenario: Create Provider
    Given a user with email "test@test.com"
    When I create an account of type "provider"
    Then I should have an empty account with id