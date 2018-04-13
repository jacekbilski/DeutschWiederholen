package org.bnb.dw.core

import java.util.*

class Quiz {

    private val questions: List<Question>
    private val random: Random

    init {
        val genderChoices = setOf(
                Choice("der", Gender.MASCULINE),
                Choice("die", Gender.FEMININE),
                Choice("das", Gender.NEUTER))
        questions = listOf(
                Question(Noun("Umgebung", Gender.FEMININE), genderChoices),
                Question(Noun("Auto", Gender.NEUTER), genderChoices))
        random = Random()
    }

    fun fetchQuestion(): Question {
        return questions[random.nextInt(questions.size)]
    }

    fun verify(question: Question, answer: Choice): Boolean {
        return question.noun.gender == answer.value
    }
}
