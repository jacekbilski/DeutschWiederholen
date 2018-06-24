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
                .flatMap { noun ->
                    listOf(QuestionPrototype(QuestionType.GENDER, noun),
                            QuestionPrototype(QuestionType.TERM_ITSELF, noun),
                            QuestionPrototype(QuestionType.TRANSLATION, noun))
                }

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
                val genderChoices = listOf(
                        Choice("der", Gender.MASCULINE),
                        Choice("die", Gender.FEMININE),
                        Choice("das", Gender.NEUTER))
                Question(prototype, genderChoices.shuffled())
            }
            QuestionType.TERM_ITSELF -> Question(prototype, proposeAnswers(prototype.noun).map { nn -> Choice(nn.gender.article + " " + nn.word, nn) })
            QuestionType.TRANSLATION -> Question(prototype, proposeAnswers(prototype.noun).map { nn -> Choice(nn.translation, nn) })
            QuestionType.PLURAL_ENDING -> Question(prototype, proposePluralEndingAnswers(prototype.noun).map { ending -> Choice(ending, ending) })
        }
    }

    private fun proposePluralEndingAnswers(noun: Noun): List<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
