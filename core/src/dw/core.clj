(ns dw.core)

(defn verify-noun [noun answer]
  (= (:gender noun) answer))
