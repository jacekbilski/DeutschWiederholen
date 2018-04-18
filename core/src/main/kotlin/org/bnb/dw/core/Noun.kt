package org.bnb.dw.core

class Noun(val word: String, val gender: Gender) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Noun

        if (word != other.word) return false
        if (gender != other.gender) return false

        return true
    }

    override fun hashCode(): Int {
        var result = word.hashCode()
        result = 31 * result + gender.hashCode()
        return result
    }
}
