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
        val nounQuestions = nouns
                .map { n -> Question(
                        QuestionType.NOUN,
                        n,
                        nouns.map { nn -> Choice(nn.gender.article + " " + nn.word, nn)}.toSet()) }
        val translationQuestions = nouns
                .map { n -> Question(
                        QuestionType.TRANSLATION,
                        n,
                        nouns.map { nn -> Choice(nn.translation, nn) }.toSet()) }
        questions = genderQuestions
                .plus(nounQuestions)
                .plus(translationQuestions)
        random = Random()
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
