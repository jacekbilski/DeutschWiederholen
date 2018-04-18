package org.bnb.dw.core

import java.util.*

class Quiz(nouns: List<Noun>) {

    private val questions: List<Question>
    private val random: Random

    init {
        val genderChoices = setOf(
                Choice("der", Gender.MASCULINE),
                Choice("die", Gender.FEMININE),
                Choice("das", Gender.NEUTER))
        questions = nouns.map { n -> Question(QuestionType.GENDER, n, genderChoices) }
        random = Random()
    }

    fun fetchQuestion(): Question {
        return questions[random.nextInt(questions.size)]
    }

    fun verify(question: Question, answer: Choice): Boolean {
        return if (question.type == QuestionType.GENDER) {
            question.noun.gender == answer.value
        } else {
            question.noun == answer.value
        }
    }
}
