package org.bnb.deutschwiederholen;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    FloatingActionButton fab = findViewById(R.id.floatingActionButton);
    fab.setOnClickListener(v -> Log.d("tag", "Clicked"));
  }
}
