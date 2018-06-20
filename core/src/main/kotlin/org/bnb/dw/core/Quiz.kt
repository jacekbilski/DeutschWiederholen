package org.bnb.dw.core

import java.util.*

class Quiz(private val repo: Repository, private val settings: Settings) {

    private val random: Random = Random()
    private val questions: Iterator<Question>

    init {
        questions = object : Iterator<Question> {
            override fun hasNext(): Boolean = true

            override fun next(): Question {
                val nouns = repo.getNouns()
                val noun = nouns[random.nextInt(nouns.size)]
                val questionType = when (random.nextInt(settings.genderWeight + settings.nounWeight + settings.translationWeight) + 1) {
                    in 1..settings.genderWeight -> QuestionType.GENDER
                    in settings.genderWeight + 1..settings.genderWeight + settings.nounWeight -> QuestionType.NOUN
                    in settings.genderWeight + settings.nounWeight + 1..settings.genderWeight + settings.nounWeight + settings.translationWeight -> QuestionType.TRANSLATION
                    else -> throw IllegalStateException("when expression not exhaustive")
                }
                val prototype = QuestionPrototype(questionType, noun)
                return prepareQuestion(prototype)
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
            QuestionType.NOUN -> Question(prototype, proposeAnswers(prototype.noun).map { nn -> Choice(nn.gender.article + " " + nn.word, nn) })
            QuestionType.TRANSLATION -> Question(prototype, proposeAnswers(prototype.noun).map { nn -> Choice(nn.translation, nn) })
        }
    }

    private fun proposeAnswers(noun: Noun): List<Noun> {
        return repo.getNouns().minus(noun).shuffled().take(4).plus(noun).shuffled()
    }

    fun fetchQuestion(): Question {
        return questions.next()
    }

    fun verify(question: Question, answer: Choice): Boolean {
        val prototype = question.prototype
        val result = when (prototype.type) {
            QuestionType.GENDER -> prototype.noun.gender == answer.value
            QuestionType.NOUN -> prototype.noun == answer.value
            QuestionType.TRANSLATION -> prototype.noun == answer.value
        }
        repo.persistAnswer(prototype, result)
        return result
    }
}
