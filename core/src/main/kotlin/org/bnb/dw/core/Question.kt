package org.bnb.dw.core

class Question(val type: QuestionType, val noun: Noun, val choices: Set<Choice>) {

    fun text(): String {
        return when (type) {
            QuestionType.GENDER -> noun.word
            QuestionType.NOUN -> noun.translation
            QuestionType.TRANSLATION -> noun.gender.article + " " + noun.word
        }
    }
}
