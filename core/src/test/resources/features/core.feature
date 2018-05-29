Feature: Core functionalities

  Scenario: Every answer the user is giving is recorded
    Given a question
    When the user answers the question
    Then the question with correctness of answer are recorded

  Scenario Outline: If a question type for a specific word was asked at most 10 times, the word is new
    Given a word
    And a question type
    And <corrNo> correct answers
    And then <incorrNo> incorrect answers
    When level of knowledge is calculated
    Then the level of knowledge is new: <new?>
    Examples:
      | corrNo | incorrNo | new? |
      | 0      | 0        | yes  |
      | 4      | 6        | yes  |
      | 10     | 0        | yes  |
      | 0      | 10       | yes  |
      | 5      | 6        | no   |
      | 11     | 0        | no   |
      | 0      | 11       | no   |

  Scenario Outline: If any of the last 20 answers to a question of given type for a specific word is incorrect, the word needs practice
    Given a word
    And a question type
    And <incorrNo> incorrect answers
    And then <corrNo> correct answers
    When level of knowledge is calculated
    Then the level of knowledge is needs practice: <needsPractice?>
    Examples:
      | incorrNo | corrNo | needsPractice? |
      | 0        | 19     | no             |
      | 1        | 10     | yes            |
      | 1        | 19     | yes            |
      | 10       | 19     | yes            |
      | 5        | 20     | no             |
      | 5        | 27     | no             |

  Scenario Outline: A word can have different levels of knowledge based on question type
    Given a word
    And a question type
    And <no1> <corr1> answers
    And then <no2> <corr2> answers
    And then <no3> <corr3> answers
    When level of knowledge is calculated
    Then the level of knowledge is <level>
    Examples:
      | no1 | corr1   | no2 | corr2     | no3 | corr3     | level          |
      | 0   | correct | 0   | incorrect | 0   | incorrect | new            |
      | 10  | correct | 0   | incorrect | 0   | incorrect | new            |
      | 0   | correct | 10  | incorrect | 0   | incorrect | new            |
      | 5   | correct | 1   | incorrect | 5   | correct   | needs practice |
      | 5   | correct | 1   | incorrect | 5   | correct   | needs practice |
      | 5   | correct | 1   | incorrect | 19  | correct   | needs practice |
      | 0   | correct | 5   | incorrect | 22  | correct   | learned        |
      | 0   | correct | 0   | incorrect | 20  | correct   | learned        |
