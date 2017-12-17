(use 'clojure.test)

(Given #"a noun (\w+) with a gender (\w+)" [noun gender]
       (println (str "Noun: " noun ", gender: " gender)))
