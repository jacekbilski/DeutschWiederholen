package org.bnb.dw.core

class QuestionPrototype(val type: QuestionType, val noun: Noun) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuestionPrototype

        if (type != other.type) return false
        if (noun != other.noun) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + noun.hashCode()
        return result
    }
}
