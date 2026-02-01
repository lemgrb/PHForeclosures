Feature: Health Check

  Scenario: Check if the application is healthy
    When the client calls "/api/health"
    Then the client receives status code 200
    And the client receives server status is UP
