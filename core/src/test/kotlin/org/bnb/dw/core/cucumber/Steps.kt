package org.bnb.dw.core.cucumber

import cucumber.api.java8.En
import org.assertj.core.api.Assertions.assertThat
import org.bnb.dw.core.*

class Steps : En {

    private var result: Boolean = false

    private var quiz: Quiz = Quiz(ArrayList())

    private var nouns: MutableMap<String, Noun> = mutableMapOf()
    private lateinit var noun: Noun

    init {
        Given("^a noun (.+) with a gender (.+)$") { word: String, gender: String ->
            noun = Noun(word, Gender.valueOf(gender.toUpperCase()), "")
            nouns[word] = noun
        }

        Given("^a noun (.+) with a translation (.+)$") {word: String, translation: String ->
            noun = Noun(word, Gender.MASCULINE, translation)
            nouns[word] = noun
        }

        When("the user chooses gender (.+)") { choice: String ->
            val goodChoice = Choice("", noun.gender)
            val currentChoice = Choice("", Gender.valueOf(choice.toUpperCase()))
            val choices = setOf(goodChoice, currentChoice)
            val question = Question(QuestionType.GENDER, noun, choices)
            result = quiz.verify(question, currentChoice)
        }

        When("the user chooses noun (.+)") { choice: String ->
            val goodChoice = Choice("", noun)
            val currentChoice = Choice("", Noun(choice, Gender.MASCULINE, ""))
            val choices = setOf(goodChoice, currentChoice)
            val question = Question(QuestionType.NOUN, noun, choices)
            result = quiz.verify(question, currentChoice)
        }

        Then("the answer was correct: (\\w+)") { correct: Boolean ->
            assertThat(result).isEqualTo(correct)
        }
    }
}
