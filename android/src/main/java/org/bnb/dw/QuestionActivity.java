package org.bnb.dw;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.bnb.dw.wrapper.Choice;
import org.bnb.dw.wrapper.Question;

import static org.bnb.dw.wrapper.Functions.fetchQuestion;
import static org.bnb.dw.wrapper.Functions.verify;

public class QuestionActivity extends AppCompatActivity {

  private RadioGroup radioGroup;
  private Question question;

  private static final String LOG_TAG = "QuestionActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question);
    displayQuestion();
  }

  private void displayQuestion() {
    Button checkButton = findViewById(R.id.checkButton);
    checkButton.setOnClickListener(this::check);
    radioGroup = findViewById(R.id.answersRadioGroup);
    question = fetchQuestion();
    printQuestion();
    setChoices();
    checkButton.setText(R.string.check);
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    Log.d(LOG_TAG, "onNewIntent");
    displayQuestion();
  }

  private void printQuestion() {
    TextView text = findViewById(R.id.questionText);
    text.setText(this.question.text);
  }

  private void setChoices() {
    radioGroup.removeAllViews();
    for (Choice choice : question.choices) {
      RadioButton newRadioButton = new RadioButton(this);
      newRadioButton.setText(choice.text);
      newRadioButton.setTag(choice.value);
      radioGroup.addView(newRadioButton);
    }
  }

  private void check(View view) {
    int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
    Log.d(LOG_TAG, "checkedRadioButtonId: " + checkedRadioButtonId);
    if (checkedRadioButtonId != -1) {
      RadioButton checkedRadioButton = findViewById(checkedRadioButtonId);
      Log.d(LOG_TAG, "checkedRadioButton: " + checkedRadioButton);
      Log.d(LOG_TAG, "checkedRadioButton.tag: " + checkedRadioButton.getTag());
      boolean result = verify(question, checkedRadioButton.getTag());
      Log.d(LOG_TAG, "Correct? " + result);
      Button checkButton = findViewById(R.id.checkButton);
      Context context = getApplicationContext();
      CharSequence text;
      if (result) {
        text = "OK";
        checkButton.setText(R.string.next);
        checkButton.setOnClickListener(this::nextQuestion);
      } else {
        text = "Errr....";
      }
      Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
      toast.show();
    }
  }

  private void nextQuestion(View view) {
    Intent intent = new Intent(this, QuestionActivity.class);
    startActivity(intent);
  }
}
