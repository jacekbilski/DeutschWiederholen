package org.bnb.dw.wrapper;

import android.util.ArraySet;

import org.bnb.dw.nouns.Noun;

import java.util.Map;
import java.util.Set;

import clojure.lang.Keyword;

public class Question {
  org.bnb.dw.core.Question question;
  public final String text;
  public final Set<Choice> choices;

  Question(org.bnb.dw.core.Question question) {
    this.question = question;
    text = (String) ((Noun) question.question).word;
    choices = new ArraySet<>();
    for (Map<Keyword, Object> choice : (Set<Map<Keyword, Object>>) question.choices) {
      choices.add(new Choice(choice));
    }
  }
}
