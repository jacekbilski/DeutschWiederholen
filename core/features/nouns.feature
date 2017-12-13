Feature: Nouns

  Scenario Outline: System tests for proper gender of a noun
    Given a noun <noun> with a gender <gender>
    And a choice of three genders
    When the user chooses gender <choice>
    Then the answer was correct: <correct>
    Examples:
      | noun | gender | choice | correct |
    