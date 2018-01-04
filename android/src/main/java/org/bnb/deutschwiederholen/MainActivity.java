package org.bnb.deutschwiederholen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    FloatingActionButton fab = findViewById(R.id.floatingActionButton);
    fab.setOnClickListener(this::fabOnClick);
  }

  private void fabOnClick(View view) {
    Log.d("tag", "Clicked");
    Intent intent = new Intent(this, QuestionActivity.class);
    startActivity(intent);
  }
}
