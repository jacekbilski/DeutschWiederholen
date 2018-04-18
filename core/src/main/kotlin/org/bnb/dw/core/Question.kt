package org.bnb.dw.core

class Question(val type: QuestionType, val noun: Noun, val choices: Set<Choice>) {

    fun text(): String {
        return if (type == QuestionType.GENDER)
            noun.word
        else
            noun.translation
    }
}
