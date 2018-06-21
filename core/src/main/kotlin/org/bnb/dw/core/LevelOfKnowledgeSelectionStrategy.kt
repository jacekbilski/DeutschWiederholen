package org.bnb.dw.core

import java.util.*

class LevelOfKnowledgeSelectionStrategy(private val settings: Settings, repository: Repository) : QuestionSelectionStrategy {
    private val random: Random = Random()
    private val progressEvaluator = ProgressEvaluator(repository)

    override fun select(): (QuestionPrototype) -> Boolean {
        val levelOfKnowledge = when (random.nextInt(settings.newTermsWeight + settings.needsPracticeTermsWeight + settings.learnedTermsWeight) + 1) {
            in 1..settings.newTermsWeight -> LevelOfKnowledge.NEW
            in settings.newTermsWeight + 1..settings.newTermsWeight + settings.needsPracticeTermsWeight -> LevelOfKnowledge.NEEDS_PRACTICE
            in settings.newTermsWeight + settings.needsPracticeTermsWeight + 1..settings.newTermsWeight + settings.needsPracticeTermsWeight + settings.learnedTermsWeight -> LevelOfKnowledge.LEARNED
            else -> throw IllegalStateException("when expression not exhaustive")
        }
        return { progressEvaluator.evaluate(it) == levelOfKnowledge }
    }

}
