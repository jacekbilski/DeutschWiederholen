package org.bnb.dw.core

import java.util.*

class Quiz(private val repository: Repository, private val settings: Settings) {

    private val random: Random = Random()
    private val questionPrototypes: List<QuestionPrototype>
    private val questionTypeSelectionStrategy: QuestionSelectionStrategy
    private val levelOfKnowledgeSelectionStrategy: QuestionSelectionStrategy
    private val questions: Iterator<Question>

    init {
        questionTypeSelectionStrategy = QuestionTypeSelectionStrategy(settings)
        levelOfKnowledgeSelectionStrategy = LevelOfKnowledgeSelectionStrategy(settings, repository)
        questionPrototypes = repository.getNouns()
                .flatMap { noun -> QuestionType.values().map { t -> QuestionPrototype(t, noun) } }

        questions = object : Iterator<Question> {
            override fun hasNext(): Boolean = true

            override fun next(): Question {
                var candidates: List<QuestionPrototype>
                do {
                    candidates = questionPrototypes
                            .filter(questionTypeSelectionStrategy.select())
                            .filter(levelOfKnowledgeSelectionStrategy.select())
                } while (candidates.isEmpty())
                return prepareQuestion(candidates[random.nextInt(candidates.size)])
            }
        }
    }

    private fun prepareQuestion(prototype: QuestionPrototype): Question {
        return when (prototype.type) {
            QuestionType.GENDER -> {
                val genderChoices = Gender.values().map { g -> Choice(g.article, g) }
                Question(prototype, genderChoices.shuffled())
            }
            QuestionType.TERM_ITSELF -> Question(prototype, proposeAnswers(prototype.noun).map { nn -> Choice(nn.gender.article + " " + nn.word, nn) })
            QuestionType.TRANSLATION -> Question(prototype, proposeAnswers(prototype.noun).map { nn -> Choice(nn.translation, nn) })
            QuestionType.PLURAL_ENDING -> Question(prototype, proposePluralEndingAnswers(prototype.noun).map { ending -> Choice(ending, ending) })
        }
    }

    private fun proposePluralEndingAnswers(noun: Noun): List<String> {
        return repository.getNouns().map { n -> n.pluralEnding }.toSet().minus(noun.pluralEnding).shuffled().take(4).plus(noun.pluralEnding).shuffled()
    }

    private fun proposeAnswers(noun: Noun): List<Noun> {
        return repository.getNouns().minus(noun).shuffled().take(4).plus(noun).shuffled()
    }

    fun fetchQuestion(): Question {
        return questions.next()
    }

    fun verify(prototype: QuestionPrototype, answer: Choice): Boolean {
        val result = when (prototype.type) {
            QuestionType.GENDER -> prototype.noun.gender == answer.value
            QuestionType.TERM_ITSELF -> prototype.noun == answer.value
            QuestionType.TRANSLATION -> prototype.noun == answer.value
            QuestionType.PLURAL_ENDING -> prototype.noun.pluralEnding == answer.value
        }
        repository.persistAnswer(prototype, result)
        return result
    }
}
