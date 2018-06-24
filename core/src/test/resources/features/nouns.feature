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

  Scenario Outline: System tests for proper plural ending when noun presented
    Given a noun <noun> with a gender <gender>, translation <translation> and a plural ending <ending>
    When the user chooses plural ending <choice>
    Then the answer was correct: <correct>
    Examples:
      | noun     | gender    | translation             | ending | choice | correct |
      | Fish     | masculine | ryba                    | -e     | -e     | true    |
      | Umgebung | feminine  | środowisko, environment | -en    | -s     | false   |

  Scenario Outline: Settings affect probability of getting a question of a specific type
    Given a word
    Given weight for gender questions of <gender_weight>
    And weight for noun questions of <noun_weight>
    And weight for translation questions of <translation_weight>
    And weight for plural ending questions of <plural_ending_weight>
    When questions are being generated
    Then probability of getting gender question is <gender_probability> percent
    And probability of getting term itself question is <noun_probability> percent
    And probability of getting translation question is <translation_probability> percent
    And probability of getting plural ending question is <plural_ending_probability> percent
    Examples:
      | gender_weight | noun_weight | translation_weight | plural_ending_weight | gender_probability | noun_probability | translation_probability | plural_ending_probability |
      | 60            | 20          | 20                 | 0                    | 60                 | 20               | 20                      | 0                         |
      | 5             | 1           | 1                  | 1                    | 62                 | 12               | 12                      | 12                        |
      | 100           | 100         | 100                | 100                  | 25                 | 25               | 25                      | 25                        |
      | 3             | 4           | 5                  | 6                    | 17                 | 22               | 28                      | 33                        |
