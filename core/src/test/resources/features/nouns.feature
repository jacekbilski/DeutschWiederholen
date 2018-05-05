Feature: Nouns

  Scenario Outline: System tests for proper gender of a noun
    Given a noun <noun> with a gender <gender> and a translation <translation>
    When the user chooses gender <choice>
    Then the answer was correct: <correct>
    Examples:
      | noun      | gender    | translation | choice    | correct |
      | Umgebung  | feminine  | środowisko  | masculine | false   |
      | Gabel     | feminine  | widelec     | feminine  | true    |
      | Schlüssel | masculine | klucz       | masculine | true    |
      | Auto      | neuter    | samochód    | feminine  | false   |

  Scenario Outline: System tests for proper noun when translation presented
    Given a noun <noun> with a gender <gender> and a translation <translation>
    When the user chooses noun <choice>
    Then the answer was correct: <correct>
    Examples:
      | noun     | gender   | translation             | choice | correct |
      | Flut     | feminine | powódź                  | Flut   | true    |
      | Umgebung | feminine | środowisko, environment | Gabel  | false   |

  Scenario Outline: System tests for proper translation when noun presented
    Given a noun <noun> with a gender <gender> and a translation <translation>
    When the user chooses translation <choice>
    Then the answer was correct: <correct>
    Examples:
      | noun     | gender   | translation             | choice   | correct |
      | Flut     | feminine | powódź                  | powódź   | true    |
      | Umgebung | feminine | środowisko, environment | samochód | false   |

    Scenario: Every answer the user is giving is recorded
      Given a question
      When the user answers the question
      Then the question with correctness of answer are recorded
