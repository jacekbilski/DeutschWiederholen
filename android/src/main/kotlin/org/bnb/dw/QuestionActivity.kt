package org.bnb.dw

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import kotlinx.android.synthetic.main.activity_question.*
import org.bnb.dw.core.*
import java.io.File
import java.io.FileReader

class QuestionActivity : AppCompatActivity() {
    private var quiz: Quiz? = null

    private val selectedChoice: RadioButton?
        get() {
            val checkedRadioButtonId = answersRadioGroup.checkedRadioButtonId
            return if (checkedRadioButtonId != -1) findViewById(checkedRadioButtonId) else null
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        initQuiz()
        prepareView()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        prepareView()
    }

    private fun initQuiz() {
        if (Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)) {
            val filesPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/dw/"
            val nouns = getNouns(filesPath)
            quiz = Quiz(nouns)
        } else {
            Log.e(this.localClassName, "Unable to load data, media not ready")
            throw IllegalStateException("Unable to read media directory")
        }
    }

    private fun getNouns(filesPath: String): List<Noun> {
        val csvReader = CSVReaderBuilder(FileReader(File(filesPath + "nouns.csv")))
                .withCSVParser(CSVParserBuilder()
                        .withSeparator('\t')
                        .build())
                .build()
        return csvReader.readAll().map { line -> Noun(line[0], Gender.of(line[1]), line[2]) }
    }

    private fun prepareView() {
        val question = quiz!!.fetchQuestion()
        printQuestion(question)
        prepareChoices(question)
        prepareCheckButton(question)
    }

    private fun printQuestion(question: Question) {
        questionText.text = question.text()
    }

    private fun prepareChoices(question: Question) {
        answersRadioGroup.removeAllViews()
        for (choice in question.choices.shuffled()) {
            val newRadioButton = RadioButton(this)
            newRadioButton.text = choice.text
            newRadioButton.tag = choice
            answersRadioGroup.addView(newRadioButton)
        }
    }

    private fun prepareCheckButton(question: Question) {
        checkButton.setOnClickListener { check(question) }
        checkButton.setText(R.string.check)
    }

    private fun check(question: Question) {
        val selectedChoice = selectedChoice
        if (selectedChoice != null) {
            val result = quiz!!.verify(question, selectedChoice.tag as Choice)
            Log.d(LOG_TAG, "Correct? $result")
            informUser(result)
            if (result)
                enableNavigationFurther()
        }
    }

    private fun informUser(result: Boolean) {
        val text = if (result) "OK" else "Errr...."
        val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun enableNavigationFurther() {
        checkButton.setText(R.string.next)
        checkButton.setOnClickListener { nextQuestion() }
    }

    private fun nextQuestion() {
        val intent = Intent(this, QuestionActivity::class.java)
        startActivity(intent)
    }

    companion object {

        private const val LOG_TAG = "QuestionActivity"
    }
}
