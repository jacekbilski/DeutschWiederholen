package org.bnb.dw.core

import java.util.*

class Quiz(private val nouns: List<Noun>) {

    private val questions: List<Question>
    private val random: Random = Random()

    init {
        val genderQuestions = nouns.map { n -> prepareQuestion(QuestionType.GENDER, n) }
        val nounQuestions = nouns.map { n -> prepareQuestion(QuestionType.NOUN, n) }
        val translationQuestions = nouns.map { n -> prepareQuestion(QuestionType.TRANSLATION, n) }
        questions = genderQuestions
                .plus(nounQuestions)
                .plus(translationQuestions)
    }

    private fun prepareQuestion(type: QuestionType, noun: Noun): Question {
        return when (type) {
            QuestionType.GENDER -> {
                val genderChoices = listOf(
                        Choice("der", Gender.MASCULINE),
                        Choice("die", Gender.FEMININE),
                        Choice("das", Gender.NEUTER))
                Question(QuestionType.GENDER, noun, genderChoices.shuffled())
            }
            QuestionType.NOUN ->
                Question(
                    QuestionType.NOUN,
                    noun,
                    proposeAnswers(noun).map { nn -> Choice(nn.gender.article + " " + nn.word, nn)})
            QuestionType.TRANSLATION ->
                Question(
                        QuestionType.TRANSLATION,
                        noun,
                        proposeAnswers(noun).map { nn -> Choice(nn.translation, nn) })
        }
    }

    private fun proposeAnswers(noun: Noun): List<Noun> {
        return nouns.minus(noun).shuffled().take(2).plus(noun).shuffled()
    }

    fun fetchQuestion(): Question {
        return questions[random.nextInt(questions.size)]
    }

    fun verify(question: Question, answer: Choice): Boolean {
        return when (question.type) {
            QuestionType.GENDER -> question.noun.gender == answer.value
            QuestionType.NOUN -> question.noun == answer.value
            QuestionType.TRANSLATION -> question.noun == answer.value
        }
    }
}
