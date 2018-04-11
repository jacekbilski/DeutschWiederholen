package org.bnb.dw.core;

import java.util.Set;

public class Question {

  public final Noun noun;
  public final Set<Choice> choices;

  public Question(Noun noun, Set<Choice> choices) {
    this.noun = noun;
    this.choices = choices;
  }

  public String text() {
    return noun.word;
  }
}
