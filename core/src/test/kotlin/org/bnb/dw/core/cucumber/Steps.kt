package org.bnb.dw.core.cucumber

import cucumber.api.java8.En
import org.assertj.core.api.Assertions.assertThat
import org.bnb.dw.core.*
import java.util.*
import kotlin.collections.ArrayList

class Steps : En {

    private var result: Boolean = false

    private val repository = RepositoryDouble()
    private var quiz: Quiz = Quiz(repository)

    private var nouns: MutableMap<String, Noun> = mutableMapOf()
    private lateinit var noun: Noun
    private lateinit var question: Question

    init {
        Given("^a noun (.+) with a gender (.+) and a translation (.+)$") { word: String, gender: String, translation: String ->
            noun = Noun(Random().nextLong(), word, Gender.valueOf(gender.toUpperCase()), translation)
            nouns[word] = noun
        }

        When("^the user chooses gender (.+)$") { choice: String ->
            val goodChoice = Choice("", noun.gender)
            val currentChoice = Choice("", Gender.valueOf(choice.toUpperCase()))
            val choices = listOf(goodChoice, currentChoice)
            val question = Question(QuestionType.GENDER, noun, choices)
            result = quiz.verify(question, currentChoice)
        }

        When("^the user chooses noun (.+)$") { choice: String ->
            val goodChoice = Choice("", noun)
            val currentChoice = Choice("", nouns[choice] ?: WRONG_NOUN)
            val choices = listOf(goodChoice, currentChoice)
            val question = Question(QuestionType.NOUN, noun, choices)
            result = quiz.verify(question, currentChoice)
        }

        When("^the user chooses translation (.+)$") { choice: String ->
            val goodChoice = Choice("", noun)
            val currentChoice = Choice("", findNounByTranslation(choice)?: WRONG_NOUN)
            val choices = listOf(goodChoice, currentChoice)
            val question = Question(QuestionType.TRANSLATION, noun, choices)
            result = quiz.verify(question, currentChoice)
        }

        Then("^the answer was correct: (\\w+)$") { correct: Boolean ->
            assertThat(result).isEqualTo(correct)
        }

        Given("^a question$") {
            noun = Noun(Random().nextLong(), "a", Gender.FEMININE, "")
            question = Question(QuestionType.NOUN, noun, ArrayList())
        }

        When("^the user answers the question$") {
            val currentChoice = Choice("", noun)
            result = quiz.verify(question, currentChoice)
        }

        Then("^the question with correctness of answer are recorded$") {
            assertThat(repository.persistedAnswers).isEqualTo(1)
        }
    }

    private fun findNounByTranslation(translation: String): Noun? {
        return nouns.entries.find { e -> e.value.translation == translation }?.value
    }

    companion object {
        private val WRONG_NOUN: Noun = Noun(Random().nextLong(), "a", Gender.MASCULINE, "b")
    }
}

class RepositoryDouble : Repository {
    var persistedAnswers: Int = 0

    override fun getNouns(): List<Noun> = ArrayList()

    override fun persistAnswer(question: Question, result: Boolean) {
        persistedAnswers++
    }
}
