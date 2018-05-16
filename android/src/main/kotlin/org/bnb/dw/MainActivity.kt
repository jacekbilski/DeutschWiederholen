package org.bnb.dw

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startQuizFloatingButton.setOnClickListener { startQuizFab() }
    }

    private fun startQuizFab() {
        val intent = Intent(this, QuestionActivity::class.java)
        startActivity(intent)
    }
}
