package org.bnb.deutschwiederholen;

import android.app.Activity;
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
    fab.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View v) {
            Log.d("tag", "Clicked");
          }
        }
    );
  }
}
