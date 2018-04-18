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
        val genderQuestions = nouns.map { n -> Question(QuestionType.GENDER, n, genderChoices) }
        val nounQuestions = nouns.map { n -> Question(QuestionType.NOUN, n, nouns.map { nn -> Choice(nn.word, nn)}.toSet()) }
        questions = genderQuestions.plus(nounQuestions)
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
