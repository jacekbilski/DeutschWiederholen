package org.bnb.dw

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.charset.Charset

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
        val url = "https://github.com/jacekbilski/DeutschWiederholen/raw/master/data/nouns.csv"

        val stringRequest = StringRequest(Request.Method.GET, url,
                { response -> save("nouns.csv", response) },
                { error -> Log.e(this.localClassName, "Getting nouns failed", error) })
        queue.add(stringRequest)
    }

    private fun save(filename: String, response: String?) {
        val fos = openFileOutput(filename, Context.MODE_PRIVATE)
        fos.write((response ?: "").toByteArray(Charset.forName("UTF-8")))
        fos.close()
        Log.d(this.localClassName, "Nouns updated")
    }
}
