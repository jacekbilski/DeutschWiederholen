package org.bnb.dw.core

class ProgressEvaluator(val repository: Repository) {
    fun evaluate(noun: Noun, questionType: QuestionType): LevelOfKnowledge {
        val answers = repository.getAnswers(noun, questionType).takeLast(20)
        if (answers.size <= 10)
            return LevelOfKnowledge.NEW
        if (answers.any { p -> !p.second })
            return LevelOfKnowledge.NEEDS_PRACTICE
        return LevelOfKnowledge.LEARNED
    }
}
