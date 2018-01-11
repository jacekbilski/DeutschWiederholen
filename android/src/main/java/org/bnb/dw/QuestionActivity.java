package org.bnb.dw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

  private static final String LOG_TAG = "QuestionActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question);
    prepareView();
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    prepareView();
  }

  private void prepareView() {
    Question question = fetchQuestion();
    printQuestion(question);
    prepareChoices(question);
    prepareCheckButton(question);
  }

  private void printQuestion(Question question) {
    TextView text = findViewById(R.id.questionText);
    text.setText(question.text);
  }

  private void prepareChoices(Question question) {
    RadioGroup radioGroup = findViewById(R.id.answersRadioGroup);
    radioGroup.removeAllViews();
    for (Choice choice : question.choices) {
      RadioButton newRadioButton = new RadioButton(this);
      newRadioButton.setText(choice.text);
      newRadioButton.setTag(choice.value);
      radioGroup.addView(newRadioButton);
    }
  }

  private void prepareCheckButton(Question question) {
    Button checkButton = findViewById(R.id.checkButton);
    checkButton.setOnClickListener(v -> check(question));
    checkButton.setText(R.string.check);
  }

  private void check(Question question) {
    RadioButton selectedChoice = getSelectedChoice();
    if (selectedChoice != null) {
      boolean result = verify(question, selectedChoice.getTag());
      Log.d(LOG_TAG, "Correct? " + result);
      informUser(result);
      if (result)
        enableNavigationFurther();
    }
  }

  private RadioButton getSelectedChoice() {
    RadioGroup radioGroup = findViewById(R.id.answersRadioGroup);
    int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
    return checkedRadioButtonId != -1 ? findViewById(checkedRadioButtonId) : null;
  }

  private void informUser(boolean result) {
    CharSequence text;
    if (result) {
      text = "OK";
    } else {
      text = "Errr....";
    }
    Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
    toast.show();
  }

  private void enableNavigationFurther() {
    Button checkButton = findViewById(R.id.checkButton);
    checkButton.setText(R.string.next);
    checkButton.setOnClickListener(v -> nextQuestion());
  }

  private void nextQuestion() {
    Intent intent = new Intent(this, QuestionActivity.class);
    startActivity(intent);
  }
}
