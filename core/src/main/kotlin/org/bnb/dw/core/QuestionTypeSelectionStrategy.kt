package org.bnb.dw.core

import java.util.*

class QuestionTypeSelectionStrategy(private val settings: Settings) : QuestionSelectionStrategy {
    private val random: Random = Random()

    override fun select(): (QuestionPrototype) -> Boolean {
        val questionType = when (random.nextInt(settings.genderWeight + settings.nounWeight + settings.translationWeight) + 1) {
            in 1..settings.genderWeight -> QuestionType.GENDER
            in settings.genderWeight + 1..settings.genderWeight + settings.nounWeight -> QuestionType.TERM_ITSELF
            in settings.genderWeight + settings.nounWeight + 1..settings.genderWeight + settings.nounWeight + settings.translationWeight -> QuestionType.TRANSLATION
            else -> throw IllegalStateException("when expression not exhaustive")
        }
        return { it.type == questionType }
    }
}
