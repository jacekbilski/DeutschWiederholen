package org.bnb.dw.core.cucumber

import cucumber.api.java8.En
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Percentage
import org.bnb.dw.core.*
import java.util.*
import kotlin.collections.ArrayList

class Steps : En {

    private var result: Boolean = false

    private val repository = RepositoryDouble()
    private var settings = Settings(1, 1, 1)
    private var quiz = Quiz(repository, settings)
    private val progressEvaluator = ProgressEvaluator(repository)

    private var nouns: MutableMap<String, Noun> = mutableMapOf()
    private lateinit var noun: Noun
    private lateinit var question: Question
    private lateinit var questionType: QuestionType
    private lateinit var levelOfKnowledge: LevelOfKnowledge
    private var generatedQuestions: List<Question> = mutableListOf()

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
            val currentChoice = Choice("", findNounByTranslation(choice) ?: WRONG_NOUN)
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
            assertThat(repository.persistedAnswers.size).isEqualTo(1)
        }

        Given("^weight for gender questions of (.+)$") { weight: Int ->
            settings = Settings(weight, settings.nounWeight, settings.translationWeight)
        }

        Given("^weight for noun questions of (.+)$") { weight: Int ->
            settings = Settings(settings.genderWeight, weight, settings.translationWeight)
        }

        Given("^weight for translation questions of (.+)$") { weight: Int ->
            settings = Settings(settings.genderWeight, settings.nounWeight, weight)
        }

        When("^questions are being generated$") {
            val repo = RepositoryDouble()
            repo.staticNouns = listOf(SOME_NOUN)
            quiz = Quiz(repo, settings)
            for (i in 1..20000) {
                generatedQuestions += quiz.fetchQuestion()
            }
        }

        Then("^probability of getting (.+) question is (.+) percent$") { type: String, probability: Double ->
            val generatedQuestionTypes: MutableMap<QuestionType, Int> = mutableMapOf(Pair(QuestionType.GENDER, 0), Pair(QuestionType.NOUN, 0), Pair(QuestionType.TRANSLATION, 0))
            for (question in generatedQuestions) {
                generatedQuestionTypes[question.type] = generatedQuestionTypes.getValue(question.type) + 1
            }
            val totalQuestions = generatedQuestionTypes.values.sum()
            val thisTypeQuestions = generatedQuestionTypes.getOrDefault(QuestionType.valueOf(type.toUpperCase()), -1)
            val expected = 100.0 * thisTypeQuestions / totalQuestions
            assertThat(probability).isCloseTo(expected, Percentage.withPercentage(5.0))
        }

        Given("^a word$") {
            noun = SOME_NOUN
        }

        Given("^a question type") {
            questionType = QuestionType.NOUN
        }

        Given("^t?h?e?n? ?(\\d+) (i?n?)correct answers$") { no: Int, corr: String ->
            question = Question(questionType, noun, emptyList())
            val correct = "" == corr
            for (i in 1..no)
                repository.persistAnswer(question, correct)
        }

        When("^level of knowledge is calculated$") {
            levelOfKnowledge = progressEvaluator.evaluate(noun, questionType)
        }

        Then("^the level of knowledge is ([^:]+): (.+)$") { targetLevel: String, levelNewStr: String ->
            val levelNew = "yes" == levelNewStr
            assertThat(levelOfKnowledge == LevelOfKnowledge.valueOf(targetLevel.replace(" ", "_").toUpperCase())).isEqualTo(levelNew)
        }

        Then("^the level of knowledge is ([^:]+)$") { level: String ->
            assertThat(levelOfKnowledge == LevelOfKnowledge.valueOf(level.replace(" ", "_").toUpperCase())).isTrue()
        }
    }

    private fun findNounByTranslation(translation: String): Noun? {
        return nouns.entries.find { e -> e.value.translation == translation }?.value
    }

    companion object {
        private val WRONG_NOUN: Noun = Noun(Random().nextLong(), "a", Gender.MASCULINE, "b")
        private val SOME_NOUN: Noun = Noun(Random().nextLong(), "Entscheidung", Gender.FEMININE, "decision")
    }
}

class RepositoryDouble : Repository {
    var persistedAnswers: List<Pair<Question, Boolean>> = ArrayList()
    var staticNouns: List<Noun> = ArrayList()

    override fun getNouns(): List<Noun> = staticNouns

    override fun persistAnswer(question: Question, result: Boolean) {
        persistedAnswers += Pair(question, result)
    }

    override fun getAnswers(noun: Noun, questionType: QuestionType): List<Pair<Question, Boolean>> {
        return persistedAnswers
    }
}
