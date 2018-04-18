Feature: Nouns

  Scenario Outline: System tests for proper gender of a noun
    Given a noun <noun> with a gender <gender>
    When the user chooses gender <choice>
    Then the answer was correct: <correct>
    Examples:
      | noun      | gender    | choice    | correct |
      | Umgebung  | feminine  | masculine | false   |
      | Gabel     | feminine  | feminine  | true    |
      | Schlüssel | masculine | masculine | true    |
      | Auto      | neuter    | feminine  | false   |

  Scenario Outline: System tests for proper noun when translation presented
    Given a noun <noun> with a translation <translation>
    When the user chooses noun <choice>
    Then the answer was correct: <correct>
    Examples:
      | noun     | translation             | choice | correct |
      | Flut     | powódź                  | Flut   | true    |
      | Umgebung | środowisko, environment | Gabel  | false   |
