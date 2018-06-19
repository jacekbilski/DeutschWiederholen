package org.bnb.dw.core

interface Repository {
    fun getNouns(): List<Noun>
    fun persistAnswer(question: QuestionPrototype, result: Boolean)
    fun getAnswers(noun: Noun, questionType: QuestionType): List<Pair<QuestionPrototype, Boolean>>
}
