package org.bnb.dw.core

interface Repository {
    fun getNouns(): List<Noun>
    fun persistAnswer(question: Question, result: Boolean)
}
