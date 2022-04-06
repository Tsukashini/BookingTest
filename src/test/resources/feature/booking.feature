Feature: Search hotel
  Scenario Outline: User is looking for a hotel
    Given User is on search screen
    When User enters the name of the <hotel> in the search field
    And User click "Search" button
    And Searching <hotel> is displayed
    Then Compare <hotel> <rating>
    Examples:
      | hotel | rating|
    |"Lisbon Serviced Apartments - Campos"| "8.3"|