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
                return when (random.nextInt(settings.genderWeight + settings.nounWeight + settings.translationWeight) + 1) {
                    in 1..settings.genderWeight -> prepareQuestion(QuestionType.GENDER, noun)
                    in settings.genderWeight + 1..settings.genderWeight + settings.nounWeight -> prepareQuestion(QuestionType.NOUN, noun)
                    in settings.genderWeight + settings.nounWeight + 1..settings.genderWeight + settings.nounWeight + settings.translationWeight -> prepareQuestion(QuestionType.TRANSLATION, noun)
                    else -> throw IllegalStateException("when expression not exhaustive")
                }
            }
        }
    }

    private fun prepareQuestion(type: QuestionType, noun: Noun): Question {
        return when (type) {
            QuestionType.GENDER -> {
                val genderChoices = listOf(
                        Choice("der", Gender.MASCULINE),
                        Choice("die", Gender.FEMININE),
                        Choice("das", Gender.NEUTER))
                Question(QuestionType.GENDER, noun, genderChoices.shuffled())
            }
            QuestionType.NOUN ->
                Question(
                        QuestionType.NOUN,
                        noun,
                        proposeAnswers(noun).map { nn -> Choice(nn.gender.article + " " + nn.word, nn) })
            QuestionType.TRANSLATION ->
                Question(
                        QuestionType.TRANSLATION,
                        noun,
                        proposeAnswers(noun).map { nn -> Choice(nn.translation, nn) })
        }
    }

    private fun proposeAnswers(noun: Noun): List<Noun> {
        return repo.getNouns().minus(noun).shuffled().take(4).plus(noun).shuffled()
    }

    fun fetchQuestion(): Question {
        return questions.next()
    }

    fun verify(question: Question, answer: Choice): Boolean {
        val result = when (question.type) {
            QuestionType.GENDER -> question.noun.gender == answer.value
            QuestionType.NOUN -> question.noun == answer.value
            QuestionType.TRANSLATION -> question.noun == answer.value
        }
        repo.persistAnswer(question, result)
        return result
    }
}
