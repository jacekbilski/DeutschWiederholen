package org.bnb.deutschwiederholen;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import dw.nouns.Noun;
import org.junit.Test;

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
        require.invoke(Clojure.read("dw.core"), Clojure.read("dw.nouns"));
        IFn verify = Clojure.var("dw.core", "verify");
        try {
            Object result = verify.invoke(noun, "f");
            assertEquals(true, result);
        } catch (Exception e) {
            System.err.println("Something went wrong: " + e.getMessage());
            e.printStackTrace(System.err);
            fail();
        }
    }
}
