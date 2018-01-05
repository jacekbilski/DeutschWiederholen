package org.bnb.deutschwiederholen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Map;
import java.util.Set;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.Keyword;
import dw.core.Question;
import dw.nouns.Noun;

public class QuestionActivity extends AppCompatActivity {

  private RadioGroup radioGroup;
  private Question question;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question);
    Button checkButton = findViewById(R.id.button);
    checkButton.setOnClickListener(this::check);
    radioGroup = findViewById(R.id.answersRadioGroup);
    fetchQuestion();
    printQuestion();
    addChoices();
  }

  private void fetchQuestion() {
    IFn require = Clojure.var("clojure.core", "require");
    require.invoke(Clojure.read("dw.core"), Clojure.read("dw.nouns"));
    IFn nextQuestion = Clojure.var("dw.nouns", "next-question");
    question = (Question) nextQuestion.invoke();
  }

  private void printQuestion() {
    TextView text = findViewById(R.id.questionText);
    Noun noun = (Noun) this.question.question;
    text.setText((String) noun.word);
  }

  private void addChoices() {
    Keyword textKeyword = Keyword.intern("text");
    Keyword valueKeyword = Keyword.intern("value");
    for (Map<Keyword, Object> choice : (Set<Map<Keyword, Object>>) question.choices) {
      RadioButton newRadioButton = new RadioButton(this);
      newRadioButton.setText((String) choice.get(textKeyword));
      newRadioButton.setTag(choice.get(valueKeyword));
      radioGroup.addView(newRadioButton);
    }
  }

  private void check(View view) {
    int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
    Log.d("questions", "checkedRadioButtonId: " + checkedRadioButtonId);
    RadioButton checkedRadioButton = findViewById(checkedRadioButtonId);
    Log.d("questions", "checkedRadioButton: " + checkedRadioButton);
    Log.d("questions", "checkedRadioButton.tag: " + checkedRadioButton.getTag());
    IFn verify = Clojure.var("dw.core", "verify");
    Object result = verify.invoke(question.question, checkedRadioButton.getTag());
    Log.d("questions", "Correct? " + result);
  }
}
