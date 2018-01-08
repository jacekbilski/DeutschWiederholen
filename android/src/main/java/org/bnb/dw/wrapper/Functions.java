package org.bnb.dw.wrapper;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

public class Functions {

  public static Question fetchQuestion() {
    IFn require = Clojure.var("clojure.core", "require");
    require.invoke(Clojure.read("org.bnb.dw.core"), Clojure.read("org.bnb.dw.nouns"));
    IFn nextQuestion = Clojure.var("org.bnb.dw.nouns", "next-question");
    return new Question((org.bnb.dw.core.Question) nextQuestion.invoke());
  }

  public static boolean verify(Question question, Object answer) {
    IFn verifyFn = Clojure.var("org.bnb.dw.core", "verify");
    return (boolean) verifyFn.invoke(question.question.question, answer);
  }
}
