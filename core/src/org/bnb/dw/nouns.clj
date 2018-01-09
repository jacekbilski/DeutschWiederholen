(ns org.bnb.dw.nouns
  (:require [org.bnb.dw.core :refer :all]))

(defrecord Noun [word gender])

(extend-protocol Quiz
  Noun
  (verify [noun answer]
    (= (:gender noun) answer))
  )

(def gender-choices
  #{{:text "der" :value :masculine}
    {:text "die" :value :feminine}
    {:text "das" :value :neuter}})

(def questions
  (->Question (->Noun "Umgebung" :feminine) gender-choices)
  (->Question (->Noun "Auto" :neuter) gender-choices))

(defn next-question []
  (rand-nth questions))
