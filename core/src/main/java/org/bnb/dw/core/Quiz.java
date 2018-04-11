package org.bnb.dw.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Quiz {

  private List<Question> questions;
  private Random random;

  public Quiz() {
    Set<Choice> genderChoices = new HashSet<>();
    genderChoices.add(new Choice("der", Gender.MASCULINE));
    genderChoices.add(new Choice("die", Gender.FEMININE));
    genderChoices.add(new Choice("das", Gender.NEUTER));
    questions = new ArrayList<>();
    questions.add(new Question(new Noun("Umgebung", Gender.FEMININE), genderChoices));
    questions.add(new Question(new Noun("Auto", Gender.NEUTER), genderChoices));
    random = new Random();
  }

  public Question fetchQuestion() {
    return questions.get(random.nextInt(questions.size()));
  }

  public boolean verify(Question question, Choice answer) {
    return question.noun.gender == answer.value;
  }
}
