package org.bnb.dw.core

enum class Gender(val article: kotlin.String) {
    MASCULINE("der"), FEMININE("die"), NEUTER("das");

    companion object {
        fun of(code: String): Gender {
            return when (code) {
                "m" -> MASCULINE
                "f" -> FEMININE
                "n" -> NEUTER
                else -> {
                    throw IllegalArgumentException("Unknown gender: '$code'")
                }
            }
        }
    }
}
