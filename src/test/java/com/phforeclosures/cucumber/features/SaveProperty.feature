Feature: Save Property

  Scenario: Save a property with all fields
    Given the property service is running
    When the client posts a property with the following details:
      | title       | Modern 3BR Home in Downtown |
      | description | Beautiful modern home with excellent amenities |
      | price       | 250000.00 |
      | address     | 123 Main Street, Philadelphia, PA 19101 |
      | imageUrls   | ["https://example.com/image1.jpg", "https://example.com/image2.jpg"] |
    Then the client receives status code 200
    And the response contains the saved property with id
    And the response contains title "Modern 3BR Home in Downtown"
    And the response contains description "Beautiful modern home with excellent amenities"
    And the response contains price 250000.00
    And the response contains address "123 Main Street, Philadelphia, PA 19101"

  Scenario: Save a property with minimum required fields
    Given the property service is running
    When the client posts a property with the following details:
      | title   | Basic Home |
      | price   | 150000.00 |
      | address | 456 Oak Avenue, Philadelphia, PA 19102 |
    Then the client receives status code 200
    And the response contains the saved property with id
    And the response contains title "Basic Home"
    And the response contains price 150000.00
    And the response contains address "456 Oak Avenue, Philadelphia, PA 19102"

  Scenario: Save a property with image URLs array
    Given the property service is running
    When the client posts a property with the following details:
      | title     | Luxury Condo |
      | price     | 500000.00 |
      | address   | 789 Pine Street, Philadelphia, PA 19103 |
      | imageUrls | ["https://example.com/luxury1.jpg", "https://example.com/luxury2.jpg", "https://example.com/luxury3.jpg"] |
    Then the client receives status code 200
    And the response contains the saved property with id
    And the response contains 3 image URLs

  Scenario: Save a property with empty description
    Given the property service is running
    When the client posts a property with the following details:
      | title       | House with No Description |
      | description | |
      | price       | 200000.00 |
      | address     | 321 Elm Street, Philadelphia, PA 19104 |
    Then the client receives status code 200
    And the response contains the saved property with id
    And the response contains empty or null description

  Scenario: Attempt to save property with invalid price format
    Given the property service is running
    When the client posts a property with invalid price "not-a-number"
    Then the client receives status code 400

  Scenario: Multiple properties can be saved
    Given the property service is running
    When the client posts a property with the following details:
      | title   | First Property |
      | price   | 100000.00 |
      | address | 111 First Street, Philadelphia, PA 19105 |
    And the client posts a property with the following details:
      | title   | Second Property |
      | price   | 200000.00 |
      | address | 222 Second Street, Philadelphia, PA 19106 |
    Then both properties are saved successfully
    And each property has a unique id