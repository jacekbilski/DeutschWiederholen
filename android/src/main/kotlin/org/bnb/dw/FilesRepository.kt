package org.bnb.dw

import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import org.bnb.dw.core.Gender
import org.bnb.dw.core.Noun
import org.bnb.dw.core.Question
import org.bnb.dw.core.Repository
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.*

class FilesRepository(private val basePath: String): Repository {
    private lateinit var nouns: List<Noun>

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
    }
}
