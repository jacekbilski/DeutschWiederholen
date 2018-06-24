package org.bnb.dw.core

class Question(val prototype: QuestionPrototype, val choices: List<Choice>) {

    fun text(): String {
        return when (prototype.type) {
            QuestionType.GENDER -> prototype.noun.word
            QuestionType.TERM_ITSELF -> prototype.noun.translation
            QuestionType.TRANSLATION -> prototype.noun.gender.article + " " + prototype.noun.word
        }
    }
}
