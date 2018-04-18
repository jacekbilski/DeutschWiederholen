package org.bnb.dw.core

class Question(val type: QuestionType, val noun: Noun, val choices: Set<Choice>) {

    fun text(): String {
        return noun.word
    }
}
