package org.bnb.dw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.bnb.dw.wrapper.Choice;
import org.bnb.dw.wrapper.Question;

import static org.bnb.dw.wrapper.Functions.fetchQuestion;
import static org.bnb.dw.wrapper.Functions.verify;

public class QuestionActivity extends AppCompatActivity {

  private RadioGroup radioGroup;
  private Question question;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question);
    Button checkButton = findViewById(R.id.checkButton);
    checkButton.setOnClickListener(this::check);
    radioGroup = findViewById(R.id.answersRadioGroup);
    question = fetchQuestion();
    printQuestion();
    addChoices();
  }

  private void printQuestion() {
    TextView text = findViewById(R.id.questionText);
    text.setText(this.question.text);
  }

  private void addChoices() {
    for (Choice choice : question.choices) {
      RadioButton newRadioButton = new RadioButton(this);
      newRadioButton.setText(choice.text);
      newRadioButton.setTag(choice.value);
      radioGroup.addView(newRadioButton);
    }
  }

  private void check(View view) {
    int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
    Log.d("questions", "checkedRadioButtonId: " + checkedRadioButtonId);
    if (checkedRadioButtonId != -1) {
      RadioButton checkedRadioButton = findViewById(checkedRadioButtonId);
      Log.d("questions", "checkedRadioButton: " + checkedRadioButton);
      Log.d("questions", "checkedRadioButton.tag: " + checkedRadioButton.getTag());
      boolean result = verify(question, checkedRadioButton.getTag());
      Log.d("questions", "Correct? " + result);
      Button checkButton = findViewById(R.id.checkButton);
      if (result) {
        checkButton.setText("OK");
      } else {
        checkButton.setText("Errr....");
      }
    }
  }
}
