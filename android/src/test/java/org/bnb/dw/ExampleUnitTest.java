package org.bnb.dw;

import org.bnb.dw.core.Question;
import org.bnb.dw.nouns.Noun;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.Keyword;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

  @Test
  public void addition_isCorrect() throws Exception {
    assertEquals(4, 2 + 2);
  }

  @Test
  public void verifyClojureCall() {
    Noun noun = new Noun("Umgebung", "f");
    IFn require = Clojure.var("clojure.core", "require");
    require.invoke(Clojure.read("org.bnb.dw.core"), Clojure.read("org.bnb.dw.nouns"));
    IFn verify = Clojure.var("org.bnb.dw.core", "verify");
    try {
      Object result = verify.invoke(noun, "f");
      assertThat(result).isEqualTo(true);
    } catch (Exception e) {
      System.err.println("Something went wrong: " + e.getMessage());
      e.printStackTrace(System.err);
      fail();
    }
  }

  @Test
  public void moreCompleteClojureRoundtrip() {
    IFn require = Clojure.var("clojure.core", "require");
    require.invoke(Clojure.read("org.bnb.dw.core"), Clojure.read("org.bnb.dw.nouns"));
    IFn nextQuestion = Clojure.var("org.bnb.dw.nouns", "next-question");
    IFn verify = Clojure.var("org.bnb.dw.core", "verify");
    IFn keyword = Clojure.var("clojure.core", "keyword");
    Question question = (Question) nextQuestion.invoke();
    Set<Map<Keyword, Object>> choices = (Set<Map<Keyword, Object>>) question.choices;
    Iterator<Map<Keyword, Object>> iterator = choices.iterator();
    iterator.next();
    Object result = verify.invoke(question.question, iterator.next().get(keyword.invoke("value")));
    assertThat(result).isEqualTo(true);
  }
}
