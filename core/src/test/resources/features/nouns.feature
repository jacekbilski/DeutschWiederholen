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

  Scenario Outline: Settings affect probability of getting a question of a specific type
    Given weight for gender questions of <gender_weight>
    And weight for noun questions of <noun_weight>
    And weight for translation questions of <translation_weight>
    When questions are being generated
    Then probability of getting gender question is <gender_probability> percent
    And probability of getting noun question is <noun_probability> percent
    And probability of getting translation question is <translation_probability> percent
    Examples:
      | gender_weight | noun_weight | translation_weight | gender_probability | noun_probability | translation_probability |
      | 60            | 20          | 20                 | 60                 | 20               | 20                      |
      | 5             | 1           | 1                  | 72                 | 14               | 14                      |
      | 100           | 100         | 100                | 33                 | 33               | 33                      |
      | 3             | 4           | 5                  | 25                 | 33               | 42                      |
