(ns dw.nouns
  (:require [dw.core :refer :all]))

(defrecord Noun [word gender])

(extend-protocol Quiz
  Noun
  (verify [noun answer]
    (= (:gender noun) answer))
  )
