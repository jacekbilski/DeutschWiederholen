(use 'clojure.test)
(use 'dw.core)

(def noun (atom []))

(def result (atom []))

(Given #"a noun ([^ ]+) with a gender (\w+)" [n gender]
       (reset! noun {:noun noun :gender gender}))

(When #"the user chooses gender (.)" [gender]
      (reset! result (verify-noun @noun gender)))

(Then #"the answer was correct: (\w+)" [correct-str]
      (let [correct (= "true" correct-str)]
        (assert (= @result correct))))
