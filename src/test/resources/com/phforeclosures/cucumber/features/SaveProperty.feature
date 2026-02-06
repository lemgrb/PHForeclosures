Feature: Listing a new property for sale

  As a user,
  I want to list a new property for sale,
  So that it can be viewed by potential buyers.

  Scenario: A property is listed with all its details
    Given a user wants to list a new property
    When the user provides the following details for the listing:
      | title       | Modern 3BR Home in Downtown                   |
      | description | Beautiful modern home with excellent amenities |
      | price       | 250000.00                                     |
      | address     | 123 Main Street, Philadelphia, PA 19101       |
      | imageUrls   | ["https://example.com/image1.jpg", "https://example.com/image2.jpg"] |
    Then the property is successfully listed
    And the new listing is assigned a unique identifier
    And the listing's details are correctly displayed

  Scenario: A property is listed with only the essential details
    Given a user wants to list a new property
    When the user provides the following essential details for the listing:
      | title   | Basic Home                              |
      | price   | 150000.00                               |
      | address | 456 Oak Avenue, Philadelphia, PA 19102  |
    Then the property is successfully listed
    And the new listing is assigned a unique identifier
    And the listing's essential details are correctly displayed

  Scenario: A property is listed with multiple images
    Given a user wants to list a new property
    When the user provides multiple images for the listing:
      | title     | Luxury Condo                              |
      | price     | 500000.00                                 |
      | address   | 789 Pine Street, Philadelphia, PA 19103   |
      | imageUrls | ["https://example.com/luxury1.jpg", "https://example.com/luxury2.jpg", "https://example.com/luxury3.jpg"] |
    Then the property is successfully listed
    And the listing includes all the provided images

  Scenario: A property is listed without a description
    Given a user wants to list a new property
    When the user provides a listing without a description:
      | title       | House with No Description                 |
      | description |                                           |
      | price       | 200000.00                                 |
      | address     | 321 Elm Street, Philadelphia, PA 19104    |
    Then the property is successfully listed
    And the listing is displayed with no description

  Scenario: Attempting to list a property with an invalid price
    Given a user wants to list a new property
    When the user attempts to provide an invalid price for the listing
    Then the system rejects the listing due to a data format error

  Scenario: Multiple properties can be listed
    Given a user wants to list multiple properties
    When the user lists a "First Property" for 100000.00 at "111 First Street, Philadelphia, PA 19105"
    And the user lists a "Second Property" for 200000.00 at "222 Second Street, Philadelphia, PA 19106"
    Then both properties are successfully listed
    And each new listing is assigned a unique identifier