(use 'clojure.test)
(use 'dw.core)
(use 'dw.nouns)

(def noun (atom []))

(def result (atom []))

(Given #"a noun ([^ ]+) with a gender (\w+)" [n gender]
       (reset! noun (->Noun n gender)))

(When #"the user chooses gender (.)" [gender]
      (reset! result (verify @noun gender)))

(Then #"the answer was correct: (\w+)" [correct-str]
      (let [correct (= "true" correct-str)]
        (assert (= @result correct))))
