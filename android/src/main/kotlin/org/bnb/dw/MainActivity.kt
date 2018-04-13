package org.bnb.dw

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startQuizFloatingButton.setOnClickListener { startQuizFab() }
        updateButton.setOnClickListener { updateData() }
    }

    private fun startQuizFab() {
        Log.d("tag", "Clicked")
        val intent = Intent(this, QuestionActivity::class.java)
        startActivity(intent)
    }

    private fun updateData() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://github.com/jacekbilski/DeutschWiederholen/raw/master/build.gradle"

        val stringRequest = StringRequest(Request.Method.GET, url,
                { response -> textView.text = "Response is: $response" },
                { _ -> textView.text = "That didn't work!" })
        queue.add(stringRequest)
    }
}
