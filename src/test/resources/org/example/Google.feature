@Run
Feature: Google search
# mvn test -DexcludedGroups="Ignore" -Dgroups="Run"
# mvn test -D"cucumber.filter.tags=@Run and (not @Ignore)"


  @Ignore
  Scenario: Search google for edgewords
    Given I am on the Google Homepage
    When I search for "BBC"
    Then "BBC" is the top result

  Scenario Outline: Search google
    Given I am on the Google Homepage
    When I search for <searchTerm>
    Then <url> is the top result
    Examples:
      | searchTerm   | url                                   |
      | "edgewords"  | "https://www.edgewordstraining.co.uk" |
      | "2i testing" | "https://2itesting.com"               |

  Scenario: Inline table
    Given I am on the Google Homepage
    When I search for "Edgewords"
    Then I see in the results
      | url                                 | title                                                    |
      | https://www.edgewordstraining.co.uk | Edgewords Training - Automated Software Testing Training |
      | https://twitter.com › edgewords     | Edgewords - Twitter                                      |