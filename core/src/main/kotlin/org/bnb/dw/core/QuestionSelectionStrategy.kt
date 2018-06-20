package org.bnb.dw.core

interface QuestionSelectionStrategy {
    fun select(): (QuestionPrototype) -> Boolean
}
