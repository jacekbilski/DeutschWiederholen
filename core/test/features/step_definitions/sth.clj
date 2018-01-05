(use 'clojure.test)
(use 'org.bnb.dw.core)
(use 'org.bnb.dw.nouns)

(def noun (atom []))

(def result (atom []))

(Given #"a noun ([^ ]+) with a gender (\w+)" [n gender]
       (reset! noun (->Noun n (keyword gender))))

(When #"the user chooses gender (\w+)" [gender]
      (reset! result (verify @noun (keyword gender))))

(Then #"the answer was correct: (\w+)" [correct-str]
      (let [correct (= "true" correct-str)]
        (assert (= @result correct))))
