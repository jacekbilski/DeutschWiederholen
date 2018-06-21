package org.bnb.dw.core

interface Repository {
    fun getNouns(): List<Noun>
    fun persistAnswer(question: QuestionPrototype, result: Boolean)
    fun getAnswers(questionPrototype: QuestionPrototype): List<Pair<QuestionPrototype, Boolean>>
}
