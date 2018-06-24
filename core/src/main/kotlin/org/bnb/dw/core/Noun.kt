package org.bnb.dw.core

class Noun(val id: Long, val word: String, val gender: Gender, val translation: String, val pluralEnding: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Noun

        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        var result = word.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}
