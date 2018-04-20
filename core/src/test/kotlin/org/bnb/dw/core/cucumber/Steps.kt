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
        Given("^a noun (.+) with a gender (.+) and a translation (.+)$") { word: String, gender: String, translation: String ->
            noun = Noun(word, Gender.valueOf(gender.toUpperCase()), translation)
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
            val currentChoice = Choice("", nouns[choice] ?: WRONG_NOUN)
            val choices = setOf(goodChoice, currentChoice)
            val question = Question(QuestionType.NOUN, noun, choices)
            result = quiz.verify(question, currentChoice)
        }

        When("the user chooses translation (.+)") { choice: String ->
            val goodChoice = Choice("", noun)
            val currentChoice = Choice("", findNounByTranslation(choice)?: WRONG_NOUN)
            val choices = setOf(goodChoice, currentChoice)
            val question = Question(QuestionType.TRANSLATION, noun, choices)
            result = quiz.verify(question, currentChoice)
        }

        Then("the answer was correct: (\\w+)") { correct: Boolean ->
            assertThat(result).isEqualTo(correct)
        }
    }

    private fun findNounByTranslation(translation: String): Noun? {
        return nouns.entries.find { e -> e.value.translation == translation }?.value
    }

    companion object {
        private val WRONG_NOUN: Noun = Noun("a", Gender.MASCULINE, "b")
    }
}
