package org.bnb.dw

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_question.*
import org.bnb.dw.core.Choice
import org.bnb.dw.core.Question
import org.bnb.dw.core.Quiz
import org.bnb.dw.core.Settings

class QuestionActivity : AppCompatActivity() {
    private var quiz: Quiz? = null
    private val repo = FilesRepository(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/dw/")

    private val selectedChoice: RadioButton?
        get() {
            val checkedRadioButtonId = answersRadioGroup.checkedRadioButtonId
            return if (checkedRadioButtonId != -1) findViewById(checkedRadioButtonId) else null
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        initQuiz()
    }

    override fun onStart() {
        super.onStart()
        prepareView()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        prepareView()
    }

    private fun initQuiz() {
        if (Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)) {
            quiz = Quiz(repo, translateSettings(PreferenceManager.getDefaultSharedPreferences(this)))
        } else {
            Log.e(this.localClassName, "Unable to load data, media not ready")
            throw IllegalStateException("Unable to read media directory")
        }
    }

    private fun translateSettings(preferences: SharedPreferences): Settings {
        val settings = Settings()
        if (preferences.contains(getString(R.string.pref_nouns_gender_weight)))
            settings.genderWeight = preferences.getInt(getString(R.string.pref_nouns_gender_weight), -1)
        if (preferences.contains(getString(R.string.pref_nouns_noun_weight)))
            settings.nounWeight = preferences.getInt(getString(R.string.pref_nouns_noun_weight), -1)
        if (preferences.contains(getString(R.string.pref_nouns_translation_weight)))
            settings.translationWeight = preferences.getInt(getString(R.string.pref_nouns_translation_weight), -1)
        return settings
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
        for (choice in question.choices) {
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
            informUser(result)
            if (result)
                nextQuestion()
        }
    }

    private fun informUser(result: Boolean) {
        val text = if (result) "OK" else "Errr...."
        val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun nextQuestion() {
        val intent = Intent(this, QuestionActivity::class.java)
        startActivity(intent)
    }
}
