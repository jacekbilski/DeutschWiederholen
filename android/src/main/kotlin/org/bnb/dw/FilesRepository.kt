package org.bnb.dw

import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import org.bnb.dw.core.*
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.*

class FilesRepository(private val basePath: String): Repository {
    private lateinit var nouns: List<Noun>
    private lateinit var answers: List<Pair<Question, Boolean>>

    override fun getNouns(): List<Noun> {
        if (!this::nouns.isInitialized)
            nouns = loadNouns()
        return nouns
    }

    private fun loadNouns(): List<Noun> {
        val csvReader = CSVReaderBuilder(FileReader(File(basePath, "nouns.csv")))
                .withSkipLines(1)
                .withCSVParser(CSVParserBuilder()
                        .withSeparator(',')
                        .build())
                .build()
        return csvReader.readAll().mapIndexed { index, line -> Noun(index.toLong(), line[0], Gender.of(line[1]), line[2]) }
    }

    override fun persistAnswer(question: Question, result: Boolean) {
        val file = File(basePath, "answers.csv")
        val writer = FileWriter(file, true)
        val timestamp = Date().time
        writer.append("NOUN,${question.type.name},${question.noun.id},$timestamp,$result\n")
        writer.close()
        if (!this::answers.isInitialized)
            answers = loadAnswers()
        answers += Pair(question, result)
    }

    private fun loadAnswers(): List<Pair<Question, Boolean>> {
        val csvReader = CSVReaderBuilder(FileReader(File(basePath, "answers.csv")))
                .withCSVParser(CSVParserBuilder()
                        .withSeparator(',')
                        .build())
                .build()
        return csvReader.readAll().map { line -> Pair(Question(QuestionType.valueOf(line[1]), getNoun(line[2].toLong()),emptyList()), "true" == line[4]) }
    }

    private fun getNoun(id: Long): Noun {
        return nouns.first { n -> n.id == id }
    }

    override fun getAnswers(noun: Noun, questionType: QuestionType): List<Pair<Question, Boolean>> {
        return answers.filter { p -> p.first.type == questionType && p.first.noun == noun }
    }
}
