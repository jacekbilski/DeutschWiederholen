package org.bnb.dw.core

class ProgressEvaluator(private val repository: Repository) {
    fun evaluate(questionPrototype: QuestionPrototype): LevelOfKnowledge {
        val answers = repository.getAnswers(questionPrototype).takeLast(20)
        if (answers.size <= 10)
            return LevelOfKnowledge.NEW
        if (answers.any { p -> !p.second })
            return LevelOfKnowledge.NEEDS_PRACTICE
        return LevelOfKnowledge.LEARNED
    }
}
