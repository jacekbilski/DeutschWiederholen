package org.bnb.dw.wrapper;

import java.util.Map;

import clojure.lang.Keyword;

public class Choice {
  public final String text;
  public final Object value;

  Choice(Map<Keyword, Object> choice) {
    text = (String) choice.get(Keyword.intern("text"));
    value = choice.get(Keyword.intern("value"));
  }
}
