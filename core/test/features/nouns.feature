Feature: Nouns

  Scenario Outline: System tests for proper gender of a noun
    Given a noun <noun> with a gender <gender>
    When the user chooses gender <choice>
    Then the answer was correct: <correct>
    Examples:
      | noun      | gender | choice | correct |
      | Umgebung  | f      | m      | false   |
      | Gabel     | f      | f      | true    |
      | Schlüssel | m      | m      | true    |
      | Auto      | n      | f      | false   |
