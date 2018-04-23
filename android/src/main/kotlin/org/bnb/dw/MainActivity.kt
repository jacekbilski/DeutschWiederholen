package org.bnb.dw

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startQuizFloatingButton.setOnClickListener { startQuizFab() }
    }

    private fun startQuizFab() {
        Log.d("tag", "Clicked")
        val intent = Intent(this, QuestionActivity::class.java)
        startActivity(intent)
    }
}
