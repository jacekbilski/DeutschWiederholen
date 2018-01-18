package org.bnb.dw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    FloatingActionButton fab = findViewById(R.id.floatingActionButton);
    fab.setOnClickListener(v -> fabOnClick());
    Button updateButton = findViewById(R.id.updateButton);
    updateButton.setOnClickListener(v -> updateData());
  }

  private void fabOnClick() {
    Log.d("tag", "Clicked");
    Intent intent = new Intent(this, QuestionActivity.class);
    startActivity(intent);
  }

  private void updateData() {
    TextView textView = findViewById(R.id.textView);
    RequestQueue queue = Volley.newRequestQueue(this);
    String url = "https://github.com/jacekbilski/DeutschWiederholen/raw/master/build.gradle";

    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
        response -> textView.setText("Response is: " + response),
        error -> textView.setText("That didn't work!"));
    queue.add(stringRequest);
  }
}
